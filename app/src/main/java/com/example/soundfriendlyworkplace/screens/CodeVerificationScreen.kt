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
fun CodeVerificationScreen(
    correo: String,
    onVerify: (String) -> Unit = {},
    onResend: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var codigo by remember { mutableStateOf("") }

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
            text = "Ingresar código",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ingresá el código de 6 dígitos enviado a $correo",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Código de verificación",
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = codigo,
            onValueChange = { nuevo ->
                if (nuevo.length <= 6 && nuevo.all { it.isDigit() }) {
                    codigo = nuevo
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("000000") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onVerify(codigo) },
            enabled = codigo.length == 6,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VioletaPrincipal
            )
        ) {
            Text("Verificar código", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onResend,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reenviar código", color = VioletaPrincipal)
        }
    }
}
