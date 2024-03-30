package com.example.arq_moderna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.arq_moderna.ui.theme.Arq_ModernaTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            btnContador()
        }
    }
}

@Composable
fun txtContador(cont: Int){
    Text(text = "$cont")
}

@Composable
fun btnContador() {
    var cont by remember { mutableStateOf(0) }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Button(onClick = { cont++ }) {
            Text("Añadir 1 al contador")
        }
        Spacer(modifier = Modifier.height(16.dp))
        txtContador(cont = cont)
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Arq_ModernaTheme {
        btnContador()
    }
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    Arq_ModernaTheme {
        txtContador(cont = 0)
    }
}
