package com.isaac.gymtracker.data.repository

import com.isaac.gymtracker.data.db.EntrenamientoDao
import com.isaac.gymtracker.data.model.Entrenamiento
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para gestionar los datos de los entrenamientos.
 *
 * Actúa como intermediario entre las fuentes de datos (el DAO local) y el resto de la
 * aplicación (generalmente los ViewModels), abstrayendo la lógica de acceso a datos.
 *
 * @param entrenamientoDao El DAO para acceder a los datos de entrenamiento locales.
 */
class EntrenamientoRepository(private val entrenamientoDao: EntrenamientoDao) {

    /**
     * Obtiene un flujo con todos los entrenamientos guardados.
     *
     * Los datos provienen directamente de la base de datos y se emiten de forma reactiva.
     *
     * @return Un [Flow] que emite la lista de todos los entrenamientos.
     */
    fun obtenerTodosEntrenamientos(): Flow<List<Entrenamiento>> {
        return entrenamientoDao.obtenerTodos()
    }

    /**
     * Inserta un nuevo entrenamiento en la fuente de datos.
     *
     * @param entrenamiento El entrenamiento que se va a guardar.
     */
    suspend fun insertarEntrenamiento(entrenamiento: Entrenamiento) {
        entrenamientoDao.insertar(entrenamiento)
    }
}
