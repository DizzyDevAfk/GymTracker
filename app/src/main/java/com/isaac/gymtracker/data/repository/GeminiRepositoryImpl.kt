// Archivo: data/repository/GeminiRepositoryImpl.kt

package com.isaac.gymtracker.data.repository


import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.Chat
import com.isaac.gymtracker.BuildConfig



class GeminiRepositoryImpl() : GeminiRepository {



    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    // 2. Iniciar la sesión de chat a partir del modelo

    private val chat: Chat = model.startChat()

    // 3. Implementación de la función principal
    override suspend fun generarRespuesta(prompt: String): String {
        return try {

            val response = chat.sendMessage(prompt)

            // Devolvemos el texto o un mensaje de error si es nulo
            response.text ?: "No se pudo generar una respuesta válida"

        } catch (e: Exception) {

            "Error al conectar con la IA: ${e.localizedMessage}"
        }
    }
}