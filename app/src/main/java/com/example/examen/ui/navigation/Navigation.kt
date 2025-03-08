package com.example.examen.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.examen.ui.common.BottomBar
import com.example.examen.ui.common.TopBar
import com.example.examen.ui.pantallaAlumnos.AlumnosScreen
import com.example.examen.ui.pantallaDetalleAlumnos.DetailAlumnoScreen
import com.example.examen.ui.pantallaDetalleInforme.DetalleInformeScreen
import com.example.examen.ui.pantallaInformes.InformesScreen
import com.example.examen.ui.pantallaLogin.LoginScreen
import com.example.examen.ui.pantallaRatones.RatonScreen
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var topBarTitle by rememberSaveable { mutableStateOf("") }
    var showBackButton by rememberSaveable { mutableStateOf(false) }
    var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }
    var isBottomFabVisible by rememberSaveable { mutableStateOf(false) }

    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = topBarTitle,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                BottomBar(navController = navController, items = rememberBottomNavItems())
            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = isBottomFabVisible) {
                FloatingActionButton(
                    onClick = {
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginDestination> {
                topBarTitle = "login screen title"
                showBackButton = false
                isBottomBarVisible = false
                isBottomFabVisible = false
                LoginScreen(onLoginSuccess = {
                    navController.navigate(HomeDestination)
                })
            }

            composable<HomeDestination> {
                topBarTitle = "alumnos"
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false
                AlumnosScreen(showSnackbar = showSnackbar, onNavigateToDetail = {
                    navController.navigate(HomeDetails(it))
                })
            }

            composable<RatonesDestination> {
                topBarTitle = "ratones"
                showBackButton = true
                isBottomBarVisible = false
                isBottomFabVisible = false
                RatonScreen(showSnackbar)
            }

            composable<HomeDetails> { backStackEntry ->
                val destination = backStackEntry.toRoute() as HomeDetails
                topBarTitle = "alumnos notas"
                showBackButton = true
                isBottomBarVisible = false
                isBottomFabVisible = false
                DetailAlumnoScreen(showSnackbar, destination.name)
            }

            composable<InformeDetalleDestination> { backStackEntry ->
                val informeId = backStackEntry.arguments?.getInt("informeId")
                informeId?.let {
                    DetalleInformeScreen(showSnackbar, informeId = it)
                } ?: Text("Informe no encontrado")
            }

            composable<InformeDestination> {
                topBarTitle = "Informes"
                showBackButton = true
                isBottomBarVisible = true
                isBottomFabVisible = true
                InformesScreen(showSnackbar = showSnackbar, navController = navController)
            }
        }
    }
}
