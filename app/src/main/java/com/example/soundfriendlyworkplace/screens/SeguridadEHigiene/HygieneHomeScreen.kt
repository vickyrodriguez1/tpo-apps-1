package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.soundfriendlyworkplace.data.model.SectorDto
import com.example.soundfriendlyworkplace.data.model.SensorDto
import com.example.soundfriendlyworkplace.ui.theme.*
import com.example.soundfriendlyworkplace.viewmodel.HygieneViewModel
import com.example.soundfriendlyworkplace.viewmodel.SectoresUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// Pantalla real: utiliza el ViewModel y actualiza al volver a primer plano.
@Composable
fun HygieneHomeScreen(
    onSectorClick: (Int, String) -> Unit = { _, _ -> },
    onViewAlerts: () -> Unit = {},
    hygieneViewModel: HygieneViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var primeraApertura by remember(hygieneViewModel) {
        mutableStateOf(true)
    }

    DisposableEffect(lifecycleOwner, hygieneViewModel) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (primeraApertura) {
                    // El ViewModel ya carga los sectores al crearse.
                    primeraApertura = false
                } else {
                    hygieneViewModel.cargarSectores()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HygieneHomeContent(
        estado = hygieneViewModel.sectoresUiState,
        onSectorClick = onSectorClick,
        onViewAlerts = onViewAlerts,
        onReintentar = {
            hygieneViewModel.cargarSectores()
        }
    )
}

// Contenido visual: independiente del ViewModel para permitir la Preview.
@Composable
private fun HygieneHomeContent(
    estado: SectoresUiState,
    onSectorClick: (Int, String) -> Unit = { _, _ -> },
    onViewAlerts: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    val conectado = estado is SectoresUiState.Exito &&
            !estado.desdeCache

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = "Inicio",
            fontSize = 20.sp,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Planta industrial",
                fontSize = 23.sp,
                color = TextoPrincipal
            )

            Text(
                text = when (estado) {
                    is SectoresUiState.Exito ->
                        if (estado.desdeCache) "Sin conexión"
                        else "✓ En línea"

                    SectoresUiState.Cargando -> "Conectando..."
                    is SectoresUiState.Error -> "Sin conexión"
                },
                fontSize = 12.sp,
                color = if (conectado) {
                    Color(0xFF2E7D32)
                } else {
                    Color.Gray
                },
                modifier = Modifier
                    .background(
                        if (conectado) Color(0xFFE4F3E8)
                        else Color(0xFFECECEC),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Seguridad e Higiene · Turno de hoy",
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Permite volver a consultar el backend en cualquier momento.
        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth(),
            enabled = estado !is SectoresUiState.Cargando,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE6DDF5),
                contentColor = TextoPrincipal
            )
        ) {
            Text("↻ Actualizar datos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (estado) {
            SectoresUiState.Cargando -> {
                Text(
                    text = "Cargando sectores...",
                    color = TextoSecundario
                )
            }

            is SectoresUiState.Error -> {
                Text(
                    text = estado.mensaje,
                    color = Color(0xFFB71C1C)
                )
            }

            is SectoresUiState.Exito -> {
                val sectores = estado.sectores

                if (estado.desdeCache) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFFEFCB),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "Datos guardados · Sin conexión",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF795500)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Estos valores son anteriores y pueden haber cambiado.",
                            fontSize = 12.sp,
                            color = Color(0xFF795500)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                val alertasPendientes = sectores.sumOf {
                    it.alertasPendientes
                }

                if (alertasPendientes > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onViewAlerts)
                            .background(
                                Color(0xFFFFE8EB),
                                RoundedCornerShape(12.dp)
                            )
                            .border(
                                1.dp,
                                Color(0xFFE57373),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ⓘ  $alertasPendientes alertas pendientes",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB71C1C)
                        )

                        Text(
                            text = "›",
                            fontSize = 24.sp,
                            color = TextoPrincipal
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }

                Text(
                    text = "Estado por sector",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    EstadoResumen(
                        cantidad = sectores.count {
                            it.estado == "NORMAL"
                        }.toString(),
                        estado = "Normal",
                        fondo = Color(0xFFE4F3E8),
                        modifier = Modifier.weight(1f)
                    )

                    EstadoResumen(
                        cantidad = sectores.count {
                            it.estado == "ATENCION"
                        }.toString(),
                        estado = "En atención",
                        fondo = Color(0xFFFFEFCB),
                        modifier = Modifier.weight(1f)
                    )

                    EstadoResumen(
                        cantidad = sectores.count {
                            it.estado == "CRITICO"
                        }.toString(),
                        estado = "Crítico",
                        fondo = Color(0xFFC62828),
                        modifier = Modifier.weight(1f),
                        textoBlanco = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (sectores.isEmpty()) {
                    Text(
                        text = "Todavía no hay sectores registrados.",
                        color = TextoSecundario
                    )
                }

                sectores.forEach { sector ->
                    val (nombreEstado, colorEstado, colorFondo) =
                        coloresPorEstado(sector.estado)

                    SectorCard(
                        nombre = sector.nombre,
                        sensor = sector.sensor?.id ?: "Sin sensor",
                        nivelRuido = sector.nivelDba?.let {
                            String.format(
                                Locale.getDefault(),
                                "%.1f dBA",
                                it
                            )
                        } ?: "Sin datos",
                        estado = nombreEstado,
                        colorEstado = colorEstado,
                        colorFondo = colorFondo,
                        hora = formatearHora(
                            sector.ultimaActualizacion
                        ),
                        onClick = {
                            onSectorClick(
                                sector.id,
                                sector.nombre
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

// Traduce los estados del backend a textos y colores de la interfaz.
private fun coloresPorEstado(
    estado: String
): Triple<String, Color, Color> {
    return when (estado) {
        "NORMAL" -> Triple(
            "Normal",
            Color(0xFF2E7D32),
            Color(0xFFE4F3E8)
        )

        "ATENCION" -> Triple(
            "Atención",
            Color(0xFF996000),
            Color(0xFFFFEFCB)
        )

        "CRITICO" -> Triple(
            "Crítico",
            Color(0xFFC62828),
            Color(0xFFFFE8EB)
        )

        else -> Triple(
            "Sin información",
            Color.Gray,
            Color(0xFFECECEC)
        )
    }
}

// Convierte la fecha UTC del backend a la hora local del dispositivo.
private fun formatearHora(fecha: String?): String {
    if (fecha == null) return "Sin actualización"

    return try {
        Instant.parse(fecha)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
    } catch (e: Exception) {
        "Sin actualización"
    }
}

@Composable
private fun SectorCard(
    nombre: String,
    sensor: String,
    nivelRuido: String,
    estado: String,
    colorEstado: Color,
    colorFondo: Color,
    hora: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                1.dp,
                colorEstado,
                RoundedCornerShape(12.dp)
            )
            .background(
                colorFondo,
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nombre,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Text(
                text = estado,
                fontSize = 12.sp,
                color = colorEstado
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "$sensor · $hora",
            fontSize = 11.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nivelRuido,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Text(
                text = "Ver detalle ›",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorEstado
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun EstadoResumen(
    cantidad: String,
    estado: String,
    fondo: Color,
    modifier: Modifier = Modifier,
    textoBlanco: Boolean = false
) {
    val colorTexto =
        if (textoBlanco) Color.White else TextoPrincipal

    Column(
        modifier = modifier
            .background(
                fondo,
                RoundedCornerShape(10.dp)
            )
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cantidad,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorTexto
        )

        Text(
            text = estado,
            fontSize = 10.sp,
            color = colorTexto
        )
    }
}

// Datos ficticios utilizados únicamente en la Preview.
@Preview(showBackground = true)
@Composable
fun HygieneHomePreview() {
    val sectoresDeEjemplo = listOf(
        SectorDto(
            id = 1,
            nombre = "Taller",
            planta = "Planta industrial",
            estado = "CRITICO",
            nivelDba = 91.0,
            exposicionPct = 106.0,
            ultimaActualizacion = "2026-11-09T13:42:00Z",
            sensor = SensorDto("S-003", "CONECTADO"),
            alertasPendientes = 1
        ),
        SectorDto(
            id = 2,
            nombre = "Producción",
            planta = "Planta industrial",
            estado = "ATENCION",
            nivelDba = 84.0,
            exposicionPct = 80.0,
            ultimaActualizacion = "2026-11-09T13:42:00Z",
            sensor = SensorDto("S-002", "CONECTADO"),
            alertasPendientes = 0
        )
    )

    SoundFriendlyWorkplaceTheme {
        HygieneHomeContent(
            estado = SectoresUiState.Exito(
                sectoresDeEjemplo
            )
        )
    }
}



