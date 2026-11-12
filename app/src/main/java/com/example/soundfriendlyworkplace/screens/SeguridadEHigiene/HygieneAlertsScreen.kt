
package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

// Pantalla real: pendiente de conectar con el backend.
@Composable
fun HygieneAlertsScreen(
    onAlertClick: (String) -> Unit = {},
    onViewSectors: () -> Unit = {},
    onViewSensors: () -> Unit = {}
) {
    HygieneAlertsContent(
        mostrarEjemplos = false,
        onAlertClick = onAlertClick,
        onViewSectors = onViewSectors,
        onViewSensors = onViewSensors
    )
}

// Contenido visual compartido por la pantalla y las Previews.
@Composable
private fun HygieneAlertsContent(
    mostrarEjemplos: Boolean,
    onAlertClick: (String) -> Unit,
    onViewSectors: () -> Unit,
    onViewSensors: () -> Unit
) {
    var pestanaSeleccionada by rememberSaveable {
        mutableStateOf("Sin resolver")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = "Alertas",
            fontSize = 20.sp,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Pestañas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color(0xFFBDA7D5),
                    RoundedCornerShape(30.dp)
                )
                .background(
                    Color(0xFFF0E8FA),
                    RoundedCornerShape(30.dp)
                )
                .padding(3.dp)
        ) {
            listOf("Sin resolver", "Resueltas").forEach { pestana ->
                val seleccionada = pestanaSeleccionada == pestana

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (seleccionada) Color(0xFFE6D5FF)
                            else Color.Transparent,
                            RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            pestanaSeleccionada = pestana
                        }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pestana,
                        fontSize = 12.sp,
                        fontWeight = if (seleccionada) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                        color = TextoPrincipal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!mostrarEjemplos) {
            // TODO: Consultar GET /api/v1/alertas.
            // TODO: Separar alertas sin resolver y resueltas
            // según los campos reales del backend.

            Text(
                text = "Información de alertas pendiente de integrar con el backend.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                color = TextoSecundario,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onViewSectors,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE9D9FF),
                    contentColor = Color(0xFF5C35A0)
                )
            ) {
                Text("Ver sectores")
            }

        } else if (pestanaSeleccionada == "Sin resolver") {

            // Datos ficticios exclusivos de la Preview.
            Text(
                text = "Priorizadas por gravedad y fecha.",
                fontSize = 11.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Alerta crítica
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFFFE8EB),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFC62828),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "ⓘ Crítico  ·  10:39",
                    fontSize = 11.sp,
                    color = Color(0xFFC62828)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Taller · alerta crítica",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "91 dBA · Sin reconocer",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onAlertClick("Taller") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5C35A0)
                    )
                ) {
                    Text("Ver y gestionar alerta")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Alerta de sensor
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFDCD4E6),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "Sensor sin conexión",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "S-004 · Depósito · desde 08:14",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onViewSensors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5C35A0)
                    )
                ) {
                    Text("Revisar sensor")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Una advertencia se resuelve al reconocerla. " +
                        "Una crítica requiere registrar una intervención.",
                fontSize = 10.sp,
                color = TextoSecundario
            )

        } else {
            // Estado vacío de ejemplo, exclusivo de la Preview.
            SinAlertas(
                onViewSectors = onViewSectors,
                mensaje = "No hay alertas resueltas"
            )
        }
    }
}

@Composable
private fun SinAlertas(
    onViewSectors: () -> Unit,
    mensaje: String = "No hay alertas pendientes"
) {
    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(
                Color(0xFFE9D9FF),
                RoundedCornerShape(14.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "✓",
            fontSize = 50.sp,
            color = Color(0xFF5C35A0)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = mensaje,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextoPrincipal,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Podés consultar el estado de los sectores desde Inicio.",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 12.sp,
        color = TextoSecundario,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = onViewSectors,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE9D9FF),
            contentColor = Color(0xFF5C35A0)
        )
    ) {
        Text("Ver sectores")
    }
}

// Preview con alertas de ejemplo.
@Preview(showBackground = true)
@Composable
private fun HygieneAlertsPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneAlertsContent(
            mostrarEjemplos = true,
            onAlertClick = {},
            onViewSectors = {},
            onViewSensors = {}
        )
    }
}

// Preview del estado real mientras falta integrar el backend.
@Preview(showBackground = true)
@Composable
private fun HygieneAlertsPendingPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneAlertsScreen()
    }
}

