package com.isaac.gymtracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isaac.gymtracker.data.model.Entrenamiento

/**
 * Clase principal de la base de datos Room para la aplicación.
 *
 * Define la configuración de la base de datos, incluyendo las entidades que contiene
 * y la versión del esquema. Sigue un patrón Singleton para garantizar que solo
 * exista una instancia de la base de datos en todo momento.
 *
 * @see Entrenamiento
 * @see EntrenamientoDao
 */
@Database(
    entities = [Entrenamiento::class],
    version = 1,
    exportSchema = false // Se recomienda mantener en false para proyectos sencillos
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Proporciona acceso al Data Access Object (DAO) para los entrenamientos.
     *
     * @return Una instancia de [EntrenamientoDao].
     */
    abstract fun entrenamientoDao(): EntrenamientoDao

    /**
     * Contiene la lógica para crear y obtener la instancia Singleton de la base de datos.
     */
    companion object {
        /**
         * La única instancia de [AppDatabase].
         * Es `Volatile` para asegurar que los cambios sean visibles para todos los hilos.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Obtiene la instancia Singleton de la base de datos.
         *
         * Crea la base de datos si aún no existe, asegurando que la operación
         * sea segura para subprocesos (thread-safe) mediante un bloque `synchronized`.
         *
         * @param context El contexto de la aplicación.
         * @return La instancia Singleton de [AppDatabase].
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_tracker_db" // Nombre del archivo de la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
