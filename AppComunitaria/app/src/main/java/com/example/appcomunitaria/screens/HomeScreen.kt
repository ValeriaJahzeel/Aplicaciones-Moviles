package com.example.appcomunitaria.screens

import androidx.compose.runtime.Composable
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcomunitaria.R

@Composable
fun HomeScreen(
    context: Context
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedButton(
            text = "Llamar a emergencias",
            icon = R.drawable.emergencia,
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:911"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomOutlinedButton(
            text = "Llamar a la policia municipal",
            icon = R.drawable.poli,
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:246 123 4567"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomOutlinedButton(
            text = "Llamar a protección civil",
            icon = R.drawable.proteccion_civil_logo_gen,
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:246 462 1725"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomOutlinedButton(
            text = "Llamar a la Cruz Roja",
            icon = R.drawable.cruzroja,
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:246 462 0920"))
                context.startActivity(intent)
            }
        )

    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF39ACE7)
        ),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text,
                modifier = Modifier.weight(1f) // Para ocupar el espacio restante y centrar el texto
            )
        }
    }
}
@Preview
@Composable
fun SimpleComposablePreview() {
    val context = androidx.compose.ui.platform.LocalContext.current
    HomeScreen(context)
}



