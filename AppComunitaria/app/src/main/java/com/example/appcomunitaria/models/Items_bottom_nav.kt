package com.example.appcomunitaria.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appcomunitaria.navigation.NavScreen

sealed class Items_bottom_nav (
    val icon: ImageVector,
    val title: String,
    val ruta: String
){
    object Item_bottom_nav1: Items_bottom_nav(
        Icons.Outlined.Notifications,
        "Alertas",
        NavScreen.AlertasNotificaciones.name
    )
    object Item_bottom_nav2: Items_bottom_nav(
        Icons.Outlined.Call,
        "Directorio",
        NavScreen.Directorio.name
    )
    object Item_bottom_nav3: Items_bottom_nav(
        Icons.Outlined.QuestionMark,
        "Encuestas",
        NavScreen.EncuestasVotaciones.name
    )
    object Item_bottom_nav4: Items_bottom_nav(
        Icons.Outlined.ChatBubble,
        "Foro",
        NavScreen.ForoComunitario.name
    )
    object Item_bottom_nav5: Items_bottom_nav(
        Icons.Outlined.WarningAmber,
        "Reportes",
        NavScreen.ReporteProblemas.name
    )
}