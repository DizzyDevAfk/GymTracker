package com.isaac.gymtracker.ui.screens // ¡Ajusta tu paquete!

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.isaac.gymtracker.navigation.Rutas
import com.isaac.gymtracker.viewmodel.EntrenamientoViewModel

@Composable
fun RegistroScreen(
    viewModel: EntrenamientoViewModel) {
    // 1. ESTADO DE LA UI
    // Usamos 'remember' y 'mutableStateOf' para que Compose sepa que estos valores pueden cambiar
    var nombreEjercicio by remember { mutableStateOf("") }
    var seriesText by remember { mutableStateOf("") }
    var repeticionesText by remember { mutableStateOf("") }
    var pesoText by remember { mutableStateOf("") }

    // 2. LAYOUT
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Registrar Nuevo Ejercicio", style = MaterialTheme.typography.headlineMedium)

        // 3. CAMPOS DE ENTRADA (Input)
        OutlinedTextField(
            value = nombreEjercicio,
            onValueChange = { nombreEjercicio = it },
            label = { Text("Nombre del Ejercicio") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField( // series
                value = seriesText,
                onValueChange = { seriesText = it.filter { it.isDigit() } }, // Solo permite números
                label = { Text("Series") },
                modifier = Modifier.weight(1f)
            )
            TextField( // repeticiones
                value = repeticionesText,
                onValueChange = { repeticionesText = it.filter { it.isDigit() } },
                label = { Text("Repeticiones") },
                modifier = Modifier.weight(1.2f)
            )
            TextField( // peso
                value = pesoText,
                onValueChange = { pesoText = it.replace(',', '.') }, // Permite coma o punto
                label = { Text("Peso (kg)") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. BOTÓN (EVENTO)
        Button(
            onClick = {
                // Validación y Conversión de tipos
                val series = seriesText.toIntOrNull() ?: 0
                val repeticiones = repeticionesText.toIntOrNull() ?: 0
                val peso = pesoText.toFloatOrNull() ?: 0f

                if (nombreEjercicio.isNotBlank() && series > 0 && peso > 0) {
                    // LLAMADA AL VIEWMODEL (Disparo del Evento)
                    viewModel.guardarEntrenamiento(
                        nombre = nombreEjercicio,
                        series = series,
                        repeticiones = repeticiones,
                        peso = peso
                    )

                    // Opcional: Limpiar campos
                    nombreEjercicio = ""
                    seriesText = ""
                    repeticionesText = ""
                    pesoText = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Guardar Entrenamiento")
        }


    }
}