package com.isaac.gymtracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isaac.gymtracker.data.model.Entrenamiento
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la entidad Entrenamiento.
 * Define las operaciones de base de datos para los entrenamientos.
 */
@Dao
interface EntrenamientoDao {

    /**
     * Inserta un nuevo entrenamiento en la base de datos.
     * Si el entrenamiento ya existe, lo reemplaza.
     *
     * @param entrenamiento El objeto de entrenamiento a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(entrenamiento: Entrenamiento)

    /**
     * Obtiene todos los registros de entrenamiento de la base de datos,
     * ordenados por fecha de forma descendente.
     *
     * @return Un [Flow] que emite la lista completa de entrenamientos
     * cada vez que los datos cambian.
     */
    @Query("SELECT * FROM tabla_entrenamientos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Entrenamiento>>
}
