package com.isaac.gymtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.isaac.gymtracker.navigation.AppNavigation
import com.isaac.gymtracker.ui.theme.GYMTRACKERTheme
import com.isaac.gymtracker.viewmodel.TrainingViewModelFactory

/**
 * Actividad principal y punto de entrada de la interfaz de usuario de la aplicación.
 *
 * Se encarga de configurar Jetpack Compose, inicializar el `ViewModel` a través de su
 * `Factory` y establecer la navegación principal de la aplicación.
 */
class MainActivity : ComponentActivity() {
    /**
     * Se llama cuando la actividad es creada por primera vez.
     *
     * Aquí se configura el contenido de la UI, se obtiene el repositorio desde la
     * clase `Application` y se crea el `TrainingViewModelFactory` para inyectarlo
     * en los ViewModels correspondientes.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Se obtiene la instancia de GymApplication para acceder a las dependencias.
            val application = application as GymApplication

            // Se crea el Factory que proveerá el Repositorio a los ViewModels.
            val viewModelFactory = TrainingViewModelFactory(application.repository)

            GYMTRACKERTheme {
                // Se inicializa la navegación de la aplicación, pasando el factory
                // para que los composables puedan obtener los ViewModels.
                AppNavigation(factory = viewModelFactory)
            }
        }
    }
}
