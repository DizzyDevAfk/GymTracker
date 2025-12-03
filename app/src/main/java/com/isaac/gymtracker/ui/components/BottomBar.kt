package com.isaac.gymtracker.ui.components // Ajusta tu paquete

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.isaac.gymtracker.navigation.Rutas
import java.util.Locale

@Composable
fun AppBottomBar(navController: NavController) {

    // Obtiene la ruta actual para saber qué ícono resaltar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    // Lista de items de navegación con sus íconos
    val items = listOf(Rutas.Registro, Rutas.Historial, Rutas.IMC)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        items.forEach { screen ->
            val isSelected = rutaActual == screen.ruta

            NavigationBarItem(
                modifier = Modifier,
                selected = isSelected,
                onClick = {
                    if (rutaActual != screen.ruta) {
                        navController.navigate(screen.ruta) {
                            // Previene la creación de múltiples copias de la misma pantalla
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Evita múltiples instancias del mismo destino en el stack
                            launchSingleTop = true
                            // Restaura el estado al volver a seleccionar el mismo item
                            restoreState = true
                        }
                    }
                },
                icon = {
                    when (screen) {
                        is Rutas.Registro -> Icon(Icons.Filled.AddCircle, contentDescription = "Registro")
                        is Rutas.Historial -> Icon(Icons.Filled.List, contentDescription = "Historial")
                        is Rutas.IMC -> Icon(Icons.Filled.FitnessCenter, contentDescription = "IMC")
                        else -> {}
                    }
                },
                label = { Text(screen.ruta.capitalize()) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.White.copy(alpha = 0.7f)
                )
            )
        }
    }
}
// Función de extensión simple para capitalizar la primera letra
fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}