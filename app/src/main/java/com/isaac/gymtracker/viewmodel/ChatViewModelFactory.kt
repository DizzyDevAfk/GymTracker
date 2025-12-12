package com.isaac.gymtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.isaac.gymtracker.data.repository.GeminiRepository


class ChatViewModelFactory(
    private val geminiRepository: GeminiRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            // Construye el ChatViewModel con la dependencia
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(geminiRepository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida para ChatViewModelFactory")
    }
}