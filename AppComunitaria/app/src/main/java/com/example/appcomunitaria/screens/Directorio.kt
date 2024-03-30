package com.example.appcomunitaria.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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



@Composable
fun Directorio(context: Context){
    val selectedContact = remember { mutableStateOf<Contact?>(null) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    ContactCardList(contactList = contactList) { contact ->
        // Crear una intención implícita para realizar una llamada telefónica
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${contact.phoneNumber}")
        // Iniciar la actividad correspondiente
        context.startActivity(intent)
    }
}


@Composable
fun ContactCardList(contactList: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn {
        items(contactList) { contact ->
            ContactCard(contact = contact, onContactClick = onContactClick)
        }
    }
}


@Composable
fun ContactItem(contact: Contact, onContactClick: (Contact) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Llama a la función de manejo de clics cuando se hace clic en un contacto
                onContactClick(contact)
            }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = contact.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodyLarge)
            Text(text = contact.additionalInfo, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun ContactCard(contact: Contact, onContactClick: (Contact) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = contact.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Información adicional:",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = contact.additionalInfo,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ContactDetailScreen(contact: Contact) {
    // Detalles del contacto
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = contact.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodyLarge)
    }
}
