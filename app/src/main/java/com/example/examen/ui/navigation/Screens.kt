package com.example.examen.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.examen.ui.common.BottomNavItem
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object HomeDestination

@Serializable
data object InformeDestination

@Serializable
data class HomeDetails (val name :String)

@Serializable
data object RatonesDestination

@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val alumnos = "Alumnos"
    val ratones = "Ratones"
    val informes = "Informes"
    return remember {
        listOf(
            BottomNavItem(
                title = alumnos,
                destination = HomeDestination,
                icon = Icons.Filled.Face
            ),BottomNavItem(
                title = ratones,
                destination = RatonesDestination,
                icon = Icons.Filled.FavoriteBorder
            ),BottomNavItem(
                title = informes,
                destination = InformeDestination,
                icon = Icons.Filled.Email
            )
        )
    }
}
