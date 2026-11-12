package com.example.soundfriendlyworkplace.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var instancia: AppDatabase? = null

    fun obtenerBaseDeDatos(context: Context): AppDatabase {
        return instancia ?: synchronized(this) {
            instancia ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "sound_friendly_workplace.db"
            ).build().also { instancia = it }
        }
    }
}


