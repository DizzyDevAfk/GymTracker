package com.isaac.gymtracker.navigation // ¡Ajusta tu paquete!

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel

// ⭐️ IMPORTS NECESARIOS
import com.isaac.gymtracker.ui.screens.HistorialScreen
import com.isaac.gymtracker.ui.screens.RegistroScreen
import com.isaac.gymtracker.ui.components.AppBottomBar
import com.isaac.gymtracker.ui.theme.screens.ChatScreen
import com.isaac.gymtracker.ui.theme.screens.IMCScreen
import com.isaac.gymtracker.viewmodel.EntrenamientoViewModel
import com.isaac.gymtracker.viewmodel.TrainingViewModelFactory
import com.isaac.gymtracker.viewmodel.ChatViewModelFactory // ⭐️ NUEVO IMPORT

@Composable
fun AppNavigation(

    trainingFactory: TrainingViewModelFactory,
    chatFactory: ChatViewModelFactory
) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomBar(navController = navController) }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Rutas.Registro.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {

            // 1. PANTALLA DE REGISTRO
            composable(Rutas.Registro.ruta) {
                // ⭐️ Usamos el trainingFactory
                val viewModel = viewModel<EntrenamientoViewModel>(factory = trainingFactory)
                RegistroScreen(
                    viewModel = viewModel
                )
            }

            // 2. PANTALLA DE HISTORIAL
            composable(Rutas.Historial.ruta) {
                // ⭐️ Usamos el trainingFactory
                val viewModel = viewModel<EntrenamientoViewModel>(factory = trainingFactory)
                HistorialScreen(
                    viewModel = viewModel
                )
            }

            // 3. PANTALLA DE IMC
            composable(Rutas.IMC.ruta) {
                IMCScreen(navController = navController)
            }

            // ⭐️ 4. PANTALLA DE CHATBOT
            composable(Rutas.Chatbot.ruta) {
                // ⭐️ CORRECCIÓN CLAVE 2: Usamos el factory del chat
                ChatScreen(factory = chatFactory)
            }
        }
    }
}
