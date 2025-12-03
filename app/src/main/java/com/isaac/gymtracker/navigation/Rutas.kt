package com.isaac.gymtracker.navigation

/**
 * Define todas las rutas de navegación de la aplicación de forma segura y centralizada.
 *
 * Al ser una clase sellada (`sealed class`), se garantiza que solo las rutas definidas
 * aquí puedan ser utilizadas, evitando errores de escritura y mejorando la mantenibilidad.
 *
 * @param ruta El identificador de la ruta como un `String`.
 */
sealed class Rutas(val ruta: String) {
    /**
     * Ruta para la pantalla de registro de nuevos entrenamientos.
     */
    data object Registro : Rutas("registro")

    /**
     * Ruta para la pantalla que muestra el historial de entrenamientos.
     */
    data object Historial : Rutas("historial")

    /**
     * Ruta para la pantalla de la calculadora de IMC.
     */
    data object IMC : Rutas("imc")

    /**
     * Ruta para la pantalla del Chatbot (funcionalidad futura).
     */
    data object Chatbot : Rutas("chatbot")
}
