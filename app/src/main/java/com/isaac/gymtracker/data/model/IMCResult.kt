package com.isaac.gymtracker.data.model

/**
 * Representa el resultado de un cálculo de Índice de Masa Corporal (IMC).
 *
 * Esta clase de datos contiene el valor numérico del IMC, su clasificación textual
 * y un color asociado para la representación visual en la interfaz de usuario.
 *
 * @param valorIMC El valor numérico del IMC calculado.
 * @param clasificacion La clasificación textual del IMC (ej: "Peso normal", "Obesidad").
 * @param colorHex El valor hexadecimal del color a utilizar para esta clasificación, representado como un `Long`.
 */
data class IMCResult(
    val valorIMC: Float,
    val clasificacion: String,
    val colorHex: Long
)
