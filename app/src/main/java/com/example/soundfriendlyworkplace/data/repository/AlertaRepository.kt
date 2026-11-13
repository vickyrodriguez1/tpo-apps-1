package com.example.soundfriendlyworkplace.data.repository

import com.example.soundfriendlyworkplace.data.model.AlertaDto
import com.example.soundfriendlyworkplace.data.remote.ApiService
import com.example.soundfriendlyworkplace.data.remote.RetrofitClient

class AlertaRepository(
    private val api: ApiService = RetrofitClient.api
) {

    suspend fun obtenerAlertas(): List<AlertaDto> {
        return api.obtenerAlertas()
    }

    suspend fun obtenerAlertaPorId(id: Int): AlertaDto {
        return api.obtenerAlertaPorId(id)
    }
}




