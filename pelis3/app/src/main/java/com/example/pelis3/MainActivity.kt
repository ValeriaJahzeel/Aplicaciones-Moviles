package com.example.pelis3

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAndSeriesApp()
        }
    }
}

data class MovieOrSeries(
    val name: String,
    val review: String,
    val durationOrSeasons: String,
    val rating: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesAndSeriesApp() {
    val moviesAndSeries = remember { mutableStateListOf<MovieOrSeries>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movies & Series") }
            )
        },
        content = {
            Surface(color = Color.LightGray) {
                MovieOrSeriesList(
                    moviesAndSeries = moviesAndSeries,
                    onAddMovieOrSeries = { movieOrSeries ->
                        moviesAndSeries.add(movieOrSeries)
                    }
                )
            }
        }
    )
}

@Composable
fun MovieOrSeriesList(
    moviesAndSeries: List<MovieOrSeries>,
    onAddMovieOrSeries: (MovieOrSeries) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        moviesAndSeries.forEach { movieOrSeries ->
            MovieOrSeriesItem(movieOrSeries = movieOrSeries)
            Spacer(modifier = Modifier.height(16.dp))
        }
        AddMovieOrSeries(onAddMovieOrSeries)
    }
}

@Composable
fun MovieOrSeriesItem(movieOrSeries: MovieOrSeries) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Name: ${movieOrSeries.name}", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Review: ${movieOrSeries.review}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Duration/Seasons: ${movieOrSeries.durationOrSeasons}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(text = "Rating: ${movieOrSeries.rating}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AddMovieOrSeries(onAddMovieOrSeries: (MovieOrSeries) -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var review by remember { mutableStateOf(TextFieldValue()) }
    var durationOrSeasons by remember { mutableStateOf(TextFieldValue()) }
    var rating by remember { mutableStateOf(0f) }

    Column {
        TextField(
            value = name.text,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = review.text,
            onValueChange = { review = it },
            label = { Text("Review") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = durationOrSeasons.text,
            onValueChange = { durationOrSeasons = it },
            label = { Text("Duration/Seasons") },
            modifier = Modifier.fillMaxWidth()
        )
        Slider(
            value = rating,
            onValueChange = { rating = it },
            valueRange = 0f..5f,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val movieOrSeries = MovieOrSeries(
                name = name.text,
                review = review.text,
                durationOrSeasons = durationOrSeasons.text,
                rating = rating
            )
            onAddMovieOrSeries(movieOrSeries)
            // Clear fields after adding
            name = TextFieldValue("")
            review = TextFieldValue("")
            durationOrSeasons = TextFieldValue("")
            rating = 0f
        }) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesAndSeriesApp()
}
