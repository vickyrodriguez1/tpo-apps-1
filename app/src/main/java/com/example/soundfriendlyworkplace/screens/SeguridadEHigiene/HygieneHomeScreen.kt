package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.SoundFriendlyWorkplaceTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HygieneHomeScreen(
    onSectorClick: () -> Unit = {}
) {
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
                text = "✓ En línea",
                fontSize = 12.sp,
                color = Color(0xFF2E7D32),
                modifier = Modifier
                    .background(
                        color = Color(0xFFE4F3E8),
                        shape = RoundedCornerShape(6.dp)
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

// TODO: Obtener las alertas activas desde el repositorio.
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                text = "ⓘ  1 alerta crítica activa",
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

        Text(
            text = "Estado por sector",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

// TODO: Calcular los estados con las mediciones reales.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            EstadoResumen(
                cantidad = "1",
                estado = "Normal",
                fondo = Color(0xFFE4F3E8),
                modifier = Modifier.weight(1f)
            )

            EstadoResumen(
                cantidad = "1",
                estado = "En atención",
                fondo = Color(0xFFFFEFCB),
                modifier = Modifier.weight(1f)
            )

            EstadoResumen(
                cantidad = "1",
                estado = "Crítico",
                fondo = Color(0xFFC62828),
                modifier = Modifier.weight(1f),
                textoBlanco = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

// TODO: Reemplazar estos datos de ejemplo por los datos reales.
        SectorCard(
            nombre = "Taller",
            onClick = onSectorClick,
            sensor = "S-003",
            nivelRuido = "91 dBA",
            dosis = "106%",
            estado = "Crítico",
            colorEstado = Color(0xFFC62828),
            colorFondo = Color(0xFFFFE8EB),
            progreso = 1f
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectorCard(
            nombre = "Producción",
            sensor = "S-002",
            nivelRuido = "84 dBA",
            dosis = "82%",
            estado = "Atención",
            colorEstado = Color(0xFF996000),
            colorFondo = Color(0xFFFFEFCB),
            progreso = 0.82f
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectorCard(
            nombre = "Administración",
            sensor = "S-001",
            nivelRuido = "65 dBA",
            dosis = "35%",
            estado = "Normal",
            colorEstado = Color(0xFF2E7D32),
            colorFondo = Color(0xFFE4F3E8),
            progreso = 0.35f
        )
    }
}

@Composable
private fun SectorCard(
    nombre: String,
    sensor: String,
    nivelRuido: String,
    dosis: String,
    estado: String,
    colorEstado: Color,
    colorFondo: Color,
    progreso: Float,
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
            text = "$sensor · 10:42",
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
                text = "$dosis de dosis  ›",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorEstado
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = colorEstado,
            trackColor = Color(0xFFD8CCDF)
        )
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
    val colorTexto = if (textoBlanco) Color.White else TextoPrincipal

    Column(
        modifier = modifier
            .background(fondo, RoundedCornerShape(10.dp))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneHomePreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneHomeScreen()
    }
}