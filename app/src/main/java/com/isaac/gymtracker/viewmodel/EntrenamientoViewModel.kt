package com.isaac.gymtracker.viewmodel // ¡Ajusta tu paquete!

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.gymtracker.data.model.Entrenamiento
import com.isaac.gymtracker.data.repository.EntrenamientoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// El ViewModel recibe el Repositorio como dependencia
class EntrenamientoViewModel(private val repository: EntrenamientoRepository) : ViewModel() {

    // Define un formateador de fecha para agrupar los datos
    private val dateFormatter = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())

    // 1. STATE (Ahora es un Mapa Agrupado)
    val entrenamientosAgrupados: StateFlow<Map<String, List<Entrenamiento>>> =
        repository.obtenerTodosEntrenamientos() // Obtiene el Flow original
            .map { list ->
                // ⭐️ LÓGICA DE AGRUPACIÓN:
                list
                    // Primero, ordenamos por fecha descendente
                    .sortedByDescending { it.fecha }
                    // Luego, agrupamos usando la fecha formateada como clave
                    .groupBy { entrenamiento ->
                        dateFormatter.format(Date(entrenamiento.fecha))
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyMap() // <-- El valor inicial ahora es un mapa vacío
            )

    // 2. EVENTS (Acciones disparadas por el usuario)
    fun guardarEntrenamiento(
        nombre: String,
        series: Int,
        repeticiones: Int,
        peso: Float
    ) {
        val nuevoEntrenamiento = Entrenamiento(
            nombreEjercicio = nombre,
            series = series,
            repeticiones = repeticiones,
            peso = peso
        )

        // Usamos viewModelScope para ejecutar la inserción en un Coroutine
        viewModelScope.launch {
            repository.insertarEntrenamiento(nuevoEntrenamiento)
        }
    }
}