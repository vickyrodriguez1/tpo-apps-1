package com.example.soundfriendlyworkplace.data.remote

import com.example.soundfriendlyworkplace.data.model.AlertaDto
import com.example.soundfriendlyworkplace.data.model.SectorDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/v1/sectores")
    suspend fun obtenerSectores(): List<SectorDto>

    @GET("api/v1/sectores/{id}")
    suspend fun obtenerSectorPorId(
        @Path("id") id: Int
    ): SectorDto

    @GET("api/v1/alertas")
    suspend fun obtenerAlertas(): List<AlertaDto>

    @GET("api/v1/alertas/{id}")
    suspend fun obtenerAlertaPorId(
        @Path("id") id: Int
    ): AlertaDto
}




