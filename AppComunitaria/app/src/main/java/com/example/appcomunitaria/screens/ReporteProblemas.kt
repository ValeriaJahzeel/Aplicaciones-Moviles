package com.example.appcomunitaria.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


private fun showToast(context: Context, errorMessage: String) {
    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
}

@Composable
fun ReporteProblemas() {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var aceptarCondiciones by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Reporte de Problemas",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción del Problema") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Dismiss keyboard */ })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = aceptarCondiciones,
                onCheckedChange = { aceptarCondiciones = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Acepto los Términos y Condiciones",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isEmpty() || descripcion.isEmpty() || !aceptarCondiciones) {
                    showToast(context, "Por favor, llene todos los campos y acepte los términos y condiciones.")
                } else {
                    showToast(context, "Reporte enviado correctamente.")
                    // Limpiar los campos después de enviar el reporte
                    nombre = ""
                    descripcion = ""
                    aceptarCondiciones = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "Enviar Reporte",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}



