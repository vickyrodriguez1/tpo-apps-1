package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneSectorScreen(
    onBack: () -> Unit = {},
    onRegisterIntervention: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .padding(18.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("‹  Taller", color = TextoPrincipal, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sensor S-003",
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Text(
                text = "ⓘ Crítico",
                fontSize = 12.sp,
                color = Color(0xFFC62828)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // TODO: Obtener mediciones y dosis reales del sector.
        Column(
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
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Nivel actual", fontSize = 12.sp, color = TextoSecundario)
                    Text(
                        "91 dBA",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                }

                Column {
                    Text("Dosis del sector", fontSize = 12.sp, color = TextoSecundario)
                    Text(
                        "106%",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC62828)
                    )
                    Text(
                        "Acumulada del turno",
                        fontSize = 10.sp,
                        color = TextoSecundario
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LinearProgressIndicator(
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFFC62828),
                trackColor = Color(0xFFD8CCDF)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Mostrar el gráfico con mediciones históricas reales.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Ruido durante el turno",
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Gráfico de mediciones pendiente de integrar",
                fontSize = 12.sp,
                color = TextoSecundario
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onRegisterIntervention,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VioletaPrincipal
            )
        ) {
            Text("Registrar intervención", color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneSectorPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneSectorScreen()
    }
}