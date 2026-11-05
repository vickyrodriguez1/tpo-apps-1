
package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneAlertDetailScreen(
    reconocida: Boolean,
    onBack: () -> Unit,
    onRecognize: () -> Unit
) {
    // TODO: Obtener los datos de la alerta desde el backend.
    // TODO: Persistir el reconocimiento mediante el backend.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(
                    text = "‹",
                    fontSize = 30.sp,
                    color = TextoPrincipal
                )
            }

            Text(
                text = "Detalle de alerta",
                fontSize = 20.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = if (reconocida) {
                "ⓘ Crítico · Reconocida"
            } else {
                "ⓘ Crítico · Sin reconocer"
            },
            fontSize = 12.sp,
            color = Color(0xFFC62828)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Sector",
            fontSize = 12.sp,
            color = Color(0xFF5C35A0)
        )

        Text(
            text = "Taller",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Text(
            text = "Hoy, 10:39 · Sensor S-003",
            fontSize = 11.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tarjeta de medición
        Row(
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Nivel actual",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "91 dBA",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
            }

            Column {
                Text(
                    text = "Estado del sector",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "Crítico",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC62828)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Información de la alerta
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
                .padding(16.dp)
        ) {
            Text(
                text = "Información de la alerta",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(14.dp))

            FilaInformacionAlerta(
                "Estado",
                if (reconocida) "Reconocida" else "Sin reconocer"
            )

            FilaInformacionAlerta("Última lectura", "10:42")
            FilaInformacionAlerta("Sector", "Taller · Nave 1")

            if (reconocida) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Reconocida por",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "María López · Hoy, 10:43",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!reconocida) {
            Button(
                onClick = onRecognize,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6D5FF),
                    contentColor = Color(0xFF5C35A0)
                )
            ) {
                Text("Reconocer alerta")
            }
        } else {
            Text(
                text = "✓ Alerta reconocida",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver a las alertas",
                color = Color(0xFF5C35A0)
            )
        }
    }
}

@Composable
private fun FilaInformacionAlerta(
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Text(
            text = valor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )
    }
}


