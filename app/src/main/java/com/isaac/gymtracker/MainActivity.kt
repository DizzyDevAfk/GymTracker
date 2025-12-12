package com.isaac.gymtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.isaac.gymtracker.navigation.AppNavigation
import com.isaac.gymtracker.ui.theme.GYMTRACKERTheme
import com.isaac.gymtracker.viewmodel.TrainingViewModelFactory
// ⭐️ NUEVOS IMPORTS
import com.isaac.gymtracker.viewmodel.ChatViewModelFactory
import com.isaac.gymtracker.data.repository.GeminiRepositoryImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val application = application as GymApplication

            // Repositorio de Entrenamiento (Existente)
            val trainingRepository = application.repository

            // ⭐️ 1. Repositorio de Gemini (Instanciado)
            val geminiRepository = GeminiRepositoryImpl()

            // Factory de Entrenamiento (Existente)
            val trainingViewModelFactory = TrainingViewModelFactory(trainingRepository)

            // ⭐️ 2. Factory de Chat (¡Creado con el repositorio del Chat!)
            val chatViewModelFactory = ChatViewModelFactory(geminiRepository)

            GYMTRACKERTheme {
                // ⭐️ 3. Pasar AMBOS factories a AppNavigation
                AppNavigation(
                    trainingFactory = trainingViewModelFactory,
                    chatFactory = chatViewModelFactory
                )
            }
        }
    }
}