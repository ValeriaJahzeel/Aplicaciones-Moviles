package com.example.appcomunitaria.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Modelo de datos para una pregunta
data class Question(val id: Int, val title: String, val author: String, val date: String, val content: String)

// Lista de ejemplo de preguntas
val questionsList = mutableListOf(
    Question(1, "Fuga de agua en xxxxx", "Usuario123", "Marzo 21, 2024", "Desde ayer se está regando el agua en la calle XXXXX"),
    Question(2, "Vendo totrtas", "Lupita123", "Marzo 20, 2024", "Vendo tortas de milanesa, jamón y más. En la calle XXXXX"),
    Question(3, "Mi perro se perdió :(", "Maria234", "Marzo 19, 2024", "Ayer se salió mi perro, es un labrador. Si alguien lo ha visto xfa llamen a XXX-XXX-XXXX")
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForoComunitario() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Foro Comunitario") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF39ACE7),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        var newQuestionTitle by remember { mutableStateOf("") }
        var newQuestionContent by remember { mutableStateOf("") }

        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(vertical = 50.dp)
        ) {
            OutlinedTextField(
                value = newQuestionTitle,
                onValueChange = { newQuestionTitle = it },
                label = { Text("¿Qué quieres preguntar?") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            OutlinedTextField(
                value = newQuestionContent,
                onValueChange = { newQuestionContent = it },
                label = { Text("Agrega más detalles...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Button(
                onClick = {
                    if (newQuestionTitle.isNotEmpty() && newQuestionContent.isNotEmpty()) {
                        val newQuestion = Question(
                            id = questionsList.size + 1,
                            title = newQuestionTitle,
                            author = "Usuario",
                            date = "Hoy",
                            content = newQuestionContent
                        )
                        questionsList.add(newQuestion)
                        newQuestionTitle = ""
                        newQuestionContent = ""
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF39ACE7),
                    contentColor = Color.White
                )
            ) {
                Text("Publicar pregunta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(questionsList) { question ->
                    QuestionItem(question = question)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun QuestionItem(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción al hacer clic en la pregunta */ }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
    )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Pregunta realizada por ${question.author} el ${question.date}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = question.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForoComunitarioPreview() {
    ForoComunitario()
}
