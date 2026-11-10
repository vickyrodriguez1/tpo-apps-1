package com.example.soundfriendlyworkplace.data.model

// Información del sensor asociado a un sector.
data class SensorDto(
    val id: String,
    val estado: String
)

// Información de un sector recibida desde la API.
data class SectorDto(
    val id: Int,
    val nombre: String,
    val planta: String,
    val estado: String,
    val nivelDba: Double?,
    val exposicionPct: Double?,
    val ultimaActualizacion: String?,
    val sensor: SensorDto?,
    val alertasPendientes: Int
)


