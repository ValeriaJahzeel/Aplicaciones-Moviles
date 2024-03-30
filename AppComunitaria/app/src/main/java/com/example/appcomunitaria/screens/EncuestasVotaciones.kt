package com.example.appcomunitaria.screens

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
import androidx.compose.ui.unit.dp

@Composable
fun EncuestasVotaciones() {
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
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = { /* Lógica para responder a la encuesta o votación */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = surveyOrPoll.title, style = MaterialTheme.typography.titleLarge)
            Text(text = surveyOrPoll.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Lógica para responder a la encuesta o votación */ },
                modifier = Modifier.fillMaxWidth()
            ) {
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
    SurveyOrPoll("¿Hay agua en tu colonoa?", "Debido a reportes, se requiere saber que colonias presentan problemas de agua", SurveyOrPollStatus.AVAILABLE),
    SurveyOrPoll("Comité ambiental", "¿Quieres unirte? Contesta la encuesta!!", SurveyOrPollStatus.AVAILABLE)
)

val expiredSurveysAndPolls = listOf(
    SurveyOrPoll("¿Subió la luz?", "Hubo reportes en el aumento de la tarifa de luz...", SurveyOrPollStatus.EXPIRED),
    SurveyOrPoll("Perros callejeros", "En la calle XXXXX se reportaron muchos perros callejeros ...", SurveyOrPollStatus.EXPIRED)
)

