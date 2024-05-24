package com.example.appcomunitaria.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcomunitaria.R
data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val imageRes: Int,
    val additionalInfo: String // Campo para información adicional
)

val contactList = listOf(
    Contact(1, "Policía municipal", "123-456-7890", R.drawable.poli, "Horario de atención: 24/7"),
    Contact(2, "Bomberos", "987-654-3210", R.drawable.bomberos, "Servicios de emergencia"),
    Contact(3, "Perrera", "555-123-4567", R.drawable.perrera, "Recoge animales abandonados"),
    Contact(4, "Presidencia municipal", "4741-123-4567", R.drawable.directorio, "Información de gobierno local"),
    Contact(5, "Centro de salud", "888-854-5147", R.drawable.imss, "Servicios médicos generales"),
    Contact(6, "Biblioteca municipal", "888-515-8754", R.drawable.biblioteca, "Horario de apertura: 9am - 6pm"),
    Contact(7, "Comisión del agua", "548-453-5545", R.drawable.agua, "Reporte de problemas con el suministro de agua")
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Directorio(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Directorio") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF39ACE7),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        ContactCardList(contactList = contactList) { contact ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${contact.phoneNumber}")
            context.startActivity(intent)
        }
    }
}

@Composable
fun ContactCardList(contactList: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 70.dp)
    ) {
        items(contactList) { contact ->
            ContactCard(contact = contact, onContactClick = onContactClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ContactCard(contact: Contact, onContactClick: (Contact) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = contact.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.additionalInfo, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DirectorioPreview() {
    Directorio(context = LocalContext.current)
}
