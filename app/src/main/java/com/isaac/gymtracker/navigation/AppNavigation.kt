package com.isaac.gymtracker.navigation // ¡Ajusta tu paquete!

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isaac.gymtracker.ui.screens.HistorialScreen
import com.isaac.gymtracker.ui.screens.RegistroScreen
import com.isaac.gymtracker.viewmodel.TrainingViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel // Importante
import com.isaac.gymtracker.ui.components.AppBottomBar
import com.isaac.gymtracker.ui.screens.IMCScreen
import com.isaac.gymtracker.viewmodel.EntrenamientoViewModel

@Composable
fun AppNavigation(factory: TrainingViewModelFactory) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomBar(navController = navController) }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Rutas.Registro.ruta,
            // ⭐️ CORRECCIÓN 1: Aplicamos el padding para evitar que la barra inferior cubra el contenido
            modifier = Modifier.padding(paddingValues)
        ) {

            // 1. PANTALLA DE REGISTRO
            composable(Rutas.Registro.ruta) {
                val viewModel = viewModel<EntrenamientoViewModel>(factory = factory)
                RegistroScreen(
                    viewModel = viewModel // ⭐️ CORRECCIÓN 2: Eliminamos el navController
                )
            }

            // 2. PANTALLA DE HISTORIAL
            composable(Rutas.Historial.ruta) {
                val viewModel = viewModel<EntrenamientoViewModel>(factory = factory)
                HistorialScreen(
                    viewModel = viewModel // ⭐️ CORRECCIÓN 2: Eliminamos el navController
                )
            }

            // 3. PANTALLA DE IMC
            composable(Rutas.IMC.ruta) {
                // Dejamos el navController aquí solo si la pantalla IMC lo necesita para navegar internamente
                IMCScreen(navController = navController)
            }
        }
    }
}
