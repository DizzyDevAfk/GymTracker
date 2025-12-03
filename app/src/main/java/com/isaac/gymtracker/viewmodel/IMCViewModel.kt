package com.isaac.gymtracker.viewmodel // Ajusta tu paquete

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.isaac.gymtracker.data.model.IMCResult

// Usamos el color ARGB de Compose para IMCResult
private val COLOR_NORMAL = 0xFF4CAF50.toLong()
private val COLOR_ALTO = 0xFFFF9800.toLong()
class IMCViewModel : ViewModel() {

    // El estado del resultado IMC: La UI observará este objeto
    var imcResultado by mutableStateOf<IMCResult?>(null)
        private set // Solo el ViewModel puede modificar esta variable

    // El estado de los inputs (no necesitamos StateFlow, usamos mutableStateOf)
    var alturaCm by mutableStateOf("")
    var pesoKg by mutableStateOf("")

    fun calcularIMC() {
        // 1. Validar y convertir inputs
        val altura = alturaCm.toFloatOrNull() ?: 0f // cm
        val peso = pesoKg.toFloatOrNull() ?: 0f // kg

        // 2. Ejecutar la lógica
        if (altura > 0 && peso > 0) {
            val alturaM = altura / 100f // Altura en metros
            val imc = peso / (alturaM * alturaM) // Fórmula: peso / (altura^2)

            // 3. Determinar Clasificación
            val (clasificacion, color) = when {
                imc < 18.5 -> "Bajo Peso" to COLOR_ALTO
                imc < 24.9 -> "Peso Normal" to COLOR_NORMAL
                imc < 29.9 -> "Sobrepeso" to COLOR_ALTO
                else -> "Obesidad" to 0xFFFF0000.toLong()
            }

            // 4. Actualizar el estado (Notifica a la UI)
            imcResultado = IMCResult(
                valorIMC = "%.2f".format(imc).toFloat(), // Formatear a 2 decimales
                clasificacion = clasificacion,
                colorHex = color
            )
        } else {
            imcResultado = null // Limpiar si los inputs son inválidos
        }
    }
}