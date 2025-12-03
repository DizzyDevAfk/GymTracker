package com.isaac.gymtracker

import android.app.Application
import com.isaac.gymtracker.data.db.AppDatabase
import com.isaac.gymtracker.data.db.EntrenamientoDao
import com.isaac.gymtracker.data.repository.EntrenamientoRepository

/**
 * Clase principal de la aplicación que gestiona el ciclo de vida de los componentes clave.
 *
 * Se utiliza para la inyección manual de dependencias, garantizando que componentes
 * como el repositorio y la base de datos se inicialicen una sola vez y estén
 * disponibles en toda la aplicación.
 */
class GymApplication : Application() {

    /**
     * Instancia única de la base de datos Room, creada de forma perezosa (lazy).
     */
    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    /**
     * Instancia única del DAO de entrenamientos, obtenida de la base de datos.
     */
    val entrenamientoDao: EntrenamientoDao by lazy { database.entrenamientoDao() }

    /**
     * Instancia única del repositorio de entrenamientos, que utiliza el DAO.
     * Este es el punto de acceso principal a los datos para los ViewModels.
     */
    val repository: EntrenamientoRepository by lazy {
        EntrenamientoRepository(entrenamientoDao)
    }
}
