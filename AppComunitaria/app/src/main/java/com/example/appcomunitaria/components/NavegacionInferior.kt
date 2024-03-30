package com.example.appcomunitaria.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.appcomunitaria.models.Items_bottom_nav
import com.example.appcomunitaria.navigation.currentRoute


@Composable
fun NavegacionInferior(
    navController: NavHostController
){
    val menu_items = listOf(
        Items_bottom_nav.Item_bottom_nav1,
        Items_bottom_nav.Item_bottom_nav2,
        Items_bottom_nav.Item_bottom_nav3,
        Items_bottom_nav.Item_bottom_nav4,
        Items_bottom_nav.Item_bottom_nav5
    )
    BottomAppBar {
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            menu_items.forEach{item ->
                val selected = currentRoute(navController) == item.ruta
                NavigationBarItem(
                    selected = selected,
                    onClick = { navController.navigate(item.ruta) },
                    icon = {
                        Icon(imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}