package com.isaac.gymtracker.data.repository

interface GeminiRepository{
    suspend fun generarRespuesta(prompt: String) : String
}