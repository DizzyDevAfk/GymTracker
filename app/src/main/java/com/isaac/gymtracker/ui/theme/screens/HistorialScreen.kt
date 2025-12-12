package com.isaac.gymtracker.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isaac.gymtracker.data.model.Entrenamiento
import com.isaac.gymtracker.viewmodel.EntrenamientoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(viewModel: EntrenamientoViewModel) {

    val gruposDeEntrenamientos by viewModel.entrenamientosAgrupados.collectAsState()
    val fechas = gruposDeEntrenamientos.keys.toList()


    // Usamos 'mutableStateOf' para que Compose redibuje al cambiar.
    var expandedDate by remember { mutableStateOf<String?>(null) } // Inicialmente nada está expandido

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Historial de Entrenamientos") })
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (gruposDeEntrenamientos.isEmpty()) {
                item {
                    Text("No hay entrenamientos registrados aún.", modifier = Modifier.padding(16.dp))
                }
            }

            fechas.forEach { fecha ->
                item {

                    val isExpanded = expandedDate == fecha
                    val entrenamientosDelDia = gruposDeEntrenamientos[fecha] ?: emptyList()

                    DayCardHeader(
                        fecha = fecha,
                        cantidad = entrenamientosDelDia.size,
                        isExpanded = isExpanded,
                        onClick = {

                            expandedDate = if (isExpanded) null else fecha
                        }
                    )


                    if (isExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                // Animación para la expansión
                                .animateContentSize(animationSpec = tween(300))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            entrenamientosDelDia.forEach { entrenamiento ->
                                // Mostramos los ejercicios individuales
                                EntrenamientoCard(entrenamiento = entrenamiento)
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun DayCardHeader(
    fecha: String,
    cantidad: Int,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Título y cantidad
            Column {
                Text(fecha, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text("$cantidad ejercicios", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
            }

            // Ícono de Expansión
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Colapsar" else "Expandir",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun EntrenamientoCard(entrenamiento: Entrenamiento) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val horaStr = dateFormat.format(Date(entrenamiento.fecha))

            Text(
                text = "${entrenamiento.nombreEjercicio} ($horaStr)",
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Series: ${entrenamiento.series}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Repeticiones: ${entrenamiento.repeticiones}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Peso: ${entrenamiento.peso} kg", style = MaterialTheme.typography.bodyMedium)
        }
    }
}