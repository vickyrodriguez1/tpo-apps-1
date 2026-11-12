package com.example.soundfriendlyworkplace.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sectores")
data class SectorEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val planta: String,
    val estado: String,
    val nivelDba: Double?,
    val ultimaActualizacion: String?,
    val sensorId: String?,
    val sensorEstado: String?,
    val alertasPendientes: Int
)

