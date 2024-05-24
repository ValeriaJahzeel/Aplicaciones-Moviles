package com.example.appcomunitaria.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuestasVotaciones() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Encuestas y Votaciones") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF39ACE7),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Encuestas y Votaciones",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AvailableSurveysAndPollsList(surveysAndPolls = availableSurveysAndPolls)
            Spacer(modifier = Modifier.height(16.dp))
            ExpiredSurveysAndPollsList(surveysAndPolls = expiredSurveysAndPolls)
        }
    }
}

@Composable
fun AvailableSurveysAndPollsList(surveysAndPolls: List<SurveyOrPoll>) {
    Text(
        text = "Disponibles:",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyColumn {
        items(surveysAndPolls.filter { it.status == SurveyOrPollStatus.AVAILABLE }) { surveyOrPoll ->
            SurveyOrPollItem(surveyOrPoll)
        }
    }
}

@Composable
fun ExpiredSurveysAndPollsList(surveysAndPolls: List<SurveyOrPoll>) {
    Text(
        text = "Vencidas:",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyColumn {
        items(surveysAndPolls.filter { it.status == SurveyOrPollStatus.EXPIRED }) { surveyOrPoll ->
            SurveyOrPollItem(surveyOrPoll)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyOrPollItem(surveyOrPoll: SurveyOrPoll) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),  // Margen solo en la parte superior
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        onClick = { /* Lógica para responder a la encuesta o votación */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = surveyOrPoll.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = surveyOrPoll.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Lógica para responder a la encuesta o votación */ },
                modifier = Modifier.fillMaxWidth() ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF39ACE7),
                    contentColor = Color.Black
            ) ){
                Text(text = "Responder")
            }
        }
    }
}

// Modelo para encuestas y votaciones
data class SurveyOrPoll(
    val title: String,
    val description: String,
    val status: SurveyOrPollStatus
)

// Enum para los estados de encuestas y votaciones
enum class SurveyOrPollStatus {
    AVAILABLE,
    EXPIRED
}

// Datos de ejemplo
val availableSurveysAndPolls = listOf(
    SurveyOrPoll("¿Hay agua en tu colonia?", "Debido a reportes, se requiere saber que colonias presentan problemas de agua", SurveyOrPollStatus.AVAILABLE),
    SurveyOrPoll("Comité ambiental", "¿Quieres unirte? ¡Contesta la encuesta!", SurveyOrPollStatus.AVAILABLE)
)

val expiredSurveysAndPolls = listOf(
    SurveyOrPoll("¿Subió la luz?", "Hubo reportes en el aumento de la tarifa de luz...", SurveyOrPollStatus.EXPIRED),
    SurveyOrPoll("Perros callejeros", "En la calle XXXXX se reportaron muchos perros callejeros...", SurveyOrPollStatus.EXPIRED)
)

@Preview(showBackground = true)
@Composable
fun EncuestasVotacionesPreview() {
    EncuestasVotaciones()
}
