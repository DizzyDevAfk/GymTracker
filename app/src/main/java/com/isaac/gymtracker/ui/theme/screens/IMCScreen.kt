package com.isaac.gymtracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.isaac.gymtracker.data.model.IMCResult
import com.isaac.gymtracker.viewmodel.IMCViewModel

/**
 * Composable que representa la pantalla de la calculadora de Índice de Masa Corporal (IMC).
 *
 * Permite al usuario introducir su peso y altura, y muestra el resultado del cálculo.
 * La lógica de negocio y el estado son gestionados por [IMCViewModel].
 *
 * @param navController El controlador de navegación (no utilizado actualmente, pero útil para futuras expansiones).
 */
@Composable
fun IMCScreen(navController: NavController) {
    val viewModel: IMCViewModel = viewModel()

    // Se observa el estado del resultado del IMC desde el ViewModel.
    val resultado = viewModel.imcResultado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)

        // Campos de texto para que el usuario introduzca su peso y altura.
        TextField(
            value = viewModel.pesoKg,
            onValueChange = { viewModel.pesoKg = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.alturaCm,
            onValueChange = { viewModel.alturaCm = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón que dispara la lógica de cálculo en el ViewModel.
        Button(
            onClick = { viewModel.calcularIMC() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // El resultado se muestra en una tarjeta solo si no es nulo.
        resultado?.let { result ->
            ResultadoCard(result = result)
        }
    }
}

/**
 * Composable que muestra el resultado del IMC en una tarjeta visualmente atractiva.
 *
 * El color de la tarjeta y la clasificación del IMC cambian según el resultado.
 *
 * @param result El objeto [IMCResult] con los datos a mostrar.
 */
@Composable
fun ResultadoCard(result: IMCResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(result.colorHex) // Color dinámico basado en el resultado.
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Tu IMC", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Text(
                text = String.format("%.1f", result.valorIMC), // Formatea el valor a un decimal.
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
            Text(
                text = result.clasificacion,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}