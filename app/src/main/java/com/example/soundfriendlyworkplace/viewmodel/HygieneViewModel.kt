
package com.example.soundfriendlyworkplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundfriendlyworkplace.data.model.SectorDto
import com.example.soundfriendlyworkplace.data.repository.SectorRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

sealed interface SectoresUiState {

    data object Cargando : SectoresUiState

    data class Exito(
        val sectores: List<SectorDto>,
        val desdeCache: Boolean = false
    ) : SectoresUiState

    data class Error(
        val mensaje: String
    ) : SectoresUiState
}

sealed interface SectorDetalleUiState {

    data object Cargando : SectorDetalleUiState

    data class Exito(
        val sector: SectorDto,
        val desdeCache: Boolean = false
    ) : SectorDetalleUiState

    data class Error(
        val mensaje: String
    ) : SectorDetalleUiState
}

class HygieneViewModel(
    private val repository: SectorRepository
) : ViewModel() {

    var sectoresUiState by mutableStateOf<SectoresUiState>(
        SectoresUiState.Cargando
    )
        private set

    var sectorDetalleUiState by mutableStateOf<SectorDetalleUiState>(
        SectorDetalleUiState.Cargando
    )
        private set

    init {
        cargarSectores()
    }

    fun cargarSectores() {
        viewModelScope.launch {
            sectoresUiState = SectoresUiState.Cargando

            sectoresUiState = try {
                val resultado = repository.obtenerSectores()

                SectoresUiState.Exito(
                    sectores = resultado.sectores,
                    desdeCache = resultado.desdeCache
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SectoresUiState.Error(
                    "No se pudieron cargar los sectores y no hay datos guardados."
                )
            }
        }
    }

    fun cargarSectorPorId(id: Int) {
        viewModelScope.launch {
            sectorDetalleUiState = SectorDetalleUiState.Cargando

            sectorDetalleUiState = try {
                val resultado = repository.obtenerSectorPorId(id)

                SectorDetalleUiState.Exito(
                    sector = resultado.sector,
                    desdeCache = resultado.desdeCache
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SectorDetalleUiState.Error(
                    "No se pudo cargar el sector y no hay datos guardados."
                )
            }
        }
    }
}


