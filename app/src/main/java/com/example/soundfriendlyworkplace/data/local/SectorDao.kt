package com.example.soundfriendlyworkplace.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SectorDao {

    // Guarda o actualiza los sectores recibidos del backend.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarSectores(sectores: List<SectorEntity>)

    // Obtiene todos los sectores almacenados localmente.
    @Query("SELECT * FROM sectores ORDER BY nombre ASC")
    suspend fun obtenerSectores(): List<SectorEntity>

    // Obtiene un sector específico mediante su ID.
    @Query("SELECT * FROM sectores WHERE id = :id LIMIT 1")
    suspend fun obtenerSectorPorId(id: Int): SectorEntity?
}


