package com.isaac.gymtracker.data.model

data class  Mensaje(
    val texto: String,
    val esUsuario: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)