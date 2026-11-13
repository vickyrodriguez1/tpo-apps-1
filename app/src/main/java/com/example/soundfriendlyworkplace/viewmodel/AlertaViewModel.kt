package com.example.soundfriendlyworkplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundfriendlyworkplace.data.model.AlertaDto
import com.example.soundfriendlyworkplace.data.repository.AlertaRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

sealed interface AlertasUiState {
    data object Cargando : AlertasUiState

    data class Exito(
        val alertas: List<AlertaDto>
    ) : AlertasUiState

    data class Error(
        val mensaje: String
    ) : AlertasUiState
}

sealed interface AlertaDetalleUiState {
    data object Cargando : AlertaDetalleUiState

    data class Exito(
        val alerta: AlertaDto
    ) : AlertaDetalleUiState

    data class Error(
        val mensaje: String
    ) : AlertaDetalleUiState
}

class AlertaViewModel(
    private val repository: AlertaRepository = AlertaRepository()
) : ViewModel() {

    var alertasUiState by mutableStateOf<AlertasUiState>(
        AlertasUiState.Cargando
    )
        private set

    var alertaDetalleUiState by mutableStateOf<AlertaDetalleUiState>(
        AlertaDetalleUiState.Cargando
    )
        private set

    init {
        cargarAlertas()
    }

    fun cargarAlertas() {
        viewModelScope.launch {
            alertasUiState = AlertasUiState.Cargando

            alertasUiState = try {
                AlertasUiState.Exito(
                    repository.obtenerAlertas()
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AlertasUiState.Error(
                    "No se pudieron cargar las alertas. Revisá la conexión."
                )
            }
        }
    }

    fun cargarAlertaPorId(id: Int) {
        viewModelScope.launch {
            alertaDetalleUiState = AlertaDetalleUiState.Cargando

            android.util.Log.d(
                "ALERTA_DETALLE",
                "Consultando alerta con ID: $id"
            )

            alertaDetalleUiState = try {
                AlertaDetalleUiState.Exito(
                    repository.obtenerAlertaPorId(id)
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: retrofit2.HttpException) {
                android.util.Log.e(
                    "ALERTA_DETALLE",
                    "Error HTTP ${e.code()} al consultar alerta ID: $id",
                    e
                )

                AlertaDetalleUiState.Error(
                    "Error HTTP ${e.code()} al consultar alerta $id"
                )
            } catch (e: Exception) {
                android.util.Log.e(
                    "ALERTA_DETALLE",
                    "Error al consultar alerta ID: $id",
                    e
                )

                AlertaDetalleUiState.Error(
                    "Error: ${e.message ?: "Desconocido"}"
                )
            }
        }
    }
}




