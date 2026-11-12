package com.example.soundfriendlyworkplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundfriendlyworkplace.data.model.SectorDto
import com.example.soundfriendlyworkplace.data.repository.SectorRepository
import kotlinx.coroutines.launch

// Estado de la lista de sectores.
sealed interface SectoresUiState {
    data object Cargando : SectoresUiState

    data class Exito(
        val sectores: List<SectorDto>
    ) : SectoresUiState

    data class Error(
        val mensaje: String
    ) : SectoresUiState
}

// Estado del detalle de un sector.
sealed interface SectorDetalleUiState {
    data object Cargando : SectorDetalleUiState

    data class Exito(
        val sector: SectorDto
    ) : SectorDetalleUiState

    data class Error(
        val mensaje: String
    ) : SectorDetalleUiState
}

class HygieneViewModel(
    private val repository: SectorRepository
) : ViewModel() {

    // Estado utilizado por la pantalla Inicio.
    var sectoresUiState by mutableStateOf<SectoresUiState>(
        SectoresUiState.Cargando
    )
        private set

    // Estado utilizado por la pantalla Detalle del sector.
    var sectorDetalleUiState by mutableStateOf<SectorDetalleUiState>(
        SectorDetalleUiState.Cargando
    )
        private set

    init {
        cargarSectores()
    }

    // Obtiene todos los sectores.
    fun cargarSectores() {
        viewModelScope.launch {
            sectoresUiState = SectoresUiState.Cargando

            sectoresUiState = try {
                SectoresUiState.Exito(
                    repository.obtenerSectores()
                )
            } catch (e: Exception) {
                SectoresUiState.Error(
                    "No se pudieron cargar los sectores. Verificá la conexión."
                )
            }
        }
    }

    // Obtiene un sector específico mediante su ID.
    fun cargarSectorPorId(id: Int) {
        viewModelScope.launch {
            sectorDetalleUiState = SectorDetalleUiState.Cargando

            sectorDetalleUiState = try {
                SectorDetalleUiState.Exito(
                    repository.obtenerSectorPorId(id)
                )
            } catch (e: Exception) {
                SectorDetalleUiState.Error(
                    "No se pudo cargar el detalle del sector. Verificá la conexión."
                )
            }
        }
    }
}

