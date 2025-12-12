package com.isaac.gymtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.gymtracker.data.model.Mensaje
import com.isaac.gymtracker.data.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel (private val geminiRepository: GeminiRepository) : ViewModel(){
    private val _mensajes = MutableStateFlow(listOf(
        Mensaje(
            texto = "¡Hola! Soy tu asistente de GymTracker. Pregúntame sobre rutinas o nutrición.",
            esUsuario = false
        )))

    val mensajes: StateFlow<List<Mensaje>> = _mensajes

    fun enviarMensaje(textoUsuario: String) {

        // 1. Añadir mensaje del usuario
        val mensajeUsuario = Mensaje(texto = textoUsuario, esUsuario = true)
        _mensajes.update { it + mensajeUsuario }

        // 2. Añadir mensaje de carga de la IA (antes de llamar a la API)
        val mensajeCargando = Mensaje("...", esUsuario = false)
        _mensajes.update { it + mensajeCargando }


        viewModelScope.launch { // ⭐️ USAMOS viewModelScope.launch

            // 3. Llamar a la API
            val respuestaIA = geminiRepository.generarRespuesta(textoUsuario)
            // 4. Crear mensaje final de la IA
            val nuevoMensajeIA = Mensaje(respuestaIA, esUsuario = false)

            // 5. Reemplazar 'Cargando...' por la respuesta final
            _mensajes.update { listaActual ->
                listaActual.dropLast(1) + nuevoMensajeIA
            }
        }
    }
}