package com.isaac.gymtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Representa un registro de entrenamiento en la base de datos.
 *
 * @param id Clave primaria autoincremental para identificar cada entrenamiento.
 * @param fecha Momento en que se registra el entrenamiento (timestamp en milisegundos).
 * @param nombreEjercicio Nombre del ejercicio realizado (ej: "Press de Banca").
 * @param series Número de series completadas.
 * @param repeticiones Número de repeticiones por serie.
 * @param peso Peso utilizado en el ejercicio (ej: en kg).
 */
@Entity(tableName = "tabla_entrenamientos")
data class Entrenamiento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: Long = Date().time,
    val nombreEjercicio: String,
    val series: Int,
    val repeticiones: Int,
    val peso: Float
)
