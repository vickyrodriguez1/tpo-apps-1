package com.example.soundfriendlyworkplace.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun RecoveryScreen(
    onSendCode: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var correo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Volver", color = VioletaPrincipal)
        }

        Spacer(modifier = Modifier.height(90.dp))

        Text(
            text = "Recuperar acceso",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ingresá tu correo electrónico y te enviaremos un código para recuperar tu acceso.",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Correo electrónico",
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("example@example.com") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onSendCode(correo.trim()) },
            enabled = correo.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VioletaPrincipal
            )
        ) {
            Text("Enviar código", color = Color.White)
        }
    }
}
