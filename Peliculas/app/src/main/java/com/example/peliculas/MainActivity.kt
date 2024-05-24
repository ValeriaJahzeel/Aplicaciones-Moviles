package com.example.peliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

// Colores personalizados
val LightBlue = Color(0xFF81D4FA)
val LightGreen = Color(0xFFA5D6A7)
val LightRed = Color(0xFFFFCDD2)

// Lista de elementos de ejemplo
val exampleMediaList = listOf(
    MovieItem(
        title = "El padrino",
        year = "1972",
        genre = "Drama",
        rating = 4.7f,
        opinion = "Una obra maestra del cine",
        imageUrl = "https://es.web.img3.acsta.net/pictures/18/06/12/12/12/0117051.jpg?coixp=49&coiyp=27"
    ),
    SerieItem(
        title = "Breaking Bad",
        year = "2008",
        genre = "Drama",
        numEpisodes = "62",
        numSeasons = "5",
        rating = 4.9f,
        opinion = "Una serie increíblemente bien escrita y actuada",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRUDBL5p70kTQ_eZpHKawWx3KUdoM0NYax_vjoSK7fQA&s"
    ),
    MovieItem(
        title = "Titanic",
        year = "1995",
        genre = "Drama",
        rating = 4.8f,
        opinion = "Una obra maestra de James Cameron",
        imageUrl = "https://m.media-amazon.com/images/I/811lT7khIrL._AC_UF894,1000_QL80_.jpg"
    ),
)

// Definición de clases de elementos de medios
sealed class MediaItem {
    abstract val title: String
    abstract val year: String
    abstract val genre: String
    abstract val rating: Float
    abstract val opinion: String
    abstract val imageUrl: String
}

data class MovieItem(
    override val title: String,
    override val year: String,
    override val genre: String,
    override val rating: Float,
    override val opinion: String,
    override val imageUrl: String
) : MediaItem()

data class SerieItem(
    override val title: String,
    override val year: String,
    override val genre: String,
    val numEpisodes: String,
    val numSeasons: String,
    override val rating: Float,
    override val opinion: String,
    override val imageUrl: String
) : MediaItem()

// Función para mostrar la lista de elementos de medios
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaList(
    mediaList: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    onEditClick: (MediaItem) -> Unit,
    onDeleteClick: (MediaItem) -> Unit
) {
    LazyColumn {
        items(mediaList) { item ->
            MediaListItem(item = item, onItemClick = onItemClick, onEditClick = onEditClick, onDeleteClick = onDeleteClick)
        }
    }
}

// Función para mostrar un elemento individual de medio
@Composable
fun MediaListItem(
    item: MediaItem,
    onItemClick: (MediaItem) -> Unit,
    onEditClick: (MediaItem) -> Unit,
    onDeleteClick: (MediaItem) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Mostrar la imagen si la URL no está vacía
            if (item.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(item.imageUrl),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Indicador visual de película o serie
            val icon = if (item is MovieItem) Icons.Default.Movie else Icons.Default.Tv
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Mostrar el título
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)

            // Espacio entre el título y los botones
            Spacer(modifier = Modifier.weight(1f))

            // Botón de editar
            IconButton(
                onClick = { onEditClick(item) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }

            // Botón de eliminar
            IconButton(
                onClick = { onDeleteClick(item) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var mediaList by remember { mutableStateOf(exampleMediaList) }
    var selectedItem by remember { mutableStateOf<MediaItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Inventario de películas/series") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add action to add media */ },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
            }
        }
        ,
        modifier = Modifier.background(LightGreen)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (mediaList.isEmpty()) {
                Text(
                    text = "¡Añade películas y series para empezar! \n Da clic en + para añadir",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            } else {
                MediaList(
                    mediaList = mediaList,
                    onItemClick = {
                        selectedItem = it
                        // Handle item click action
                    },
                    onEditClick = {
                        selectedItem = it
                        // Handle edit action
                    },
                    onDeleteClick = {
                        selectedItem = it
                        // Handle delete action
                    }
                )
            }
        }
    }
}



// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}
