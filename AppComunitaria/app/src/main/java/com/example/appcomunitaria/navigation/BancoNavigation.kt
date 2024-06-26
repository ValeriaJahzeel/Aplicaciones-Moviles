package com.example.appcomunitaria.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appcomunitaria.login.LogIn
import com.example.appcomunitaria.screens.AlertasNotificaciones
import com.example.appcomunitaria.screens.Directorio
import com.example.appcomunitaria.screens.EncuestasVotaciones
import com.example.appcomunitaria.screens.ForoComunitario
import com.example.appcomunitaria.screens.HomeScreen
import com.example.appcomunitaria.screens.ReporteProblemas
import com.example.appcomunitaria.screens.UserProfile

@Composable
fun BancoNavigation(
    navController: NavController,
    context: Context
){
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavScreen.HomeScreen.name
    ){
        composable(NavScreen.Login.name){
            LogIn(navController)
        }
        composable(NavScreen.HomeScreen.name){
            HomeScreen(context)
        }
        composable(NavScreen.ForoComunitario.name){
            ForoComunitario()
        }
        composable(NavScreen.Directorio.name){
            Directorio(context)
        }
        composable(NavScreen.EncuestasVotaciones.name){
            EncuestasVotaciones()
        }
        composable(NavScreen.ReporteProblemas.name){
            ReporteProblemas()
        }
        composable(NavScreen.AlertasNotificaciones.name){
            AlertasNotificaciones()
        }
        composable(NavScreen.UserProfile.name) {
            UserProfile()
        }
    }
}

