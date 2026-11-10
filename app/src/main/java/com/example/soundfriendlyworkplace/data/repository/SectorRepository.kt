package com.example.soundfriendlyworkplace.data.repository

import com.example.soundfriendlyworkplace.data.model.SectorDto
import com.example.soundfriendlyworkplace.data.remote.ApiService
import com.example.soundfriendlyworkplace.data.remote.RetrofitClient

class SectorRepository(
    private val api: ApiService = RetrofitClient.api
) {

    suspend fun obtenerSectores(): List<SectorDto> {
        return api.obtenerSectores()
    }

    suspend fun obtenerSectorPorId(id: Int): SectorDto {
        return api.obtenerSectorPorId(id)
    }
}
