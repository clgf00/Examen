    package com.example.examen.ui.navigation

    import androidx.compose.animation.AnimatedVisibility
    import androidx.compose.animation.fadeIn
    import androidx.compose.animation.fadeOut
    import androidx.compose.animation.slideInVertically
    import androidx.compose.animation.slideOutVertically
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.SnackbarDuration
    import androidx.compose.material3.SnackbarHost
    import androidx.compose.material3.SnackbarHostState
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
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
        var isTopBarVisible by rememberSaveable { mutableStateOf(true) }


        val showSnackbar: (String) -> Unit = { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                if (isTopBarVisible) {
                    TopBar(
                        title = topBarTitle
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = isBottomBarVisible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                ) {
                    BottomBar(navController = navController, items = rememberBottomNavItems())
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = LoginDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<LoginDestination> {
                    topBarTitle = "Login"
                    showBackButton = false
                    isBottomBarVisible = false
                    isTopBarVisible = false
                    LoginScreen(onLoginSuccess = {
                        navController.navigate(HomeDestination)
                    })
                }

                composable<HomeDestination> {
                    topBarTitle = "Alumnos"
                    showBackButton = false
                    isBottomBarVisible = true
                    isTopBarVisible = true
                    AlumnosScreen(showSnackbar = showSnackbar, onNavigateToDetail = {
                        navController.navigate(NotasDestination(it))
                    })
                }

                composable<RatonesDestination> {
                    topBarTitle = "Ratones"
                    showBackButton = true
                    isTopBarVisible = false
                    isBottomBarVisible = false
                    RatonScreen(
                        showSnackbar = showSnackbar,
                        onBackClick = { navController.navigateUp() }
                    )
                }


                composable<NotasDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute() as NotasDestination
                    topBarTitle = "Notas de Alumnos"
                    showBackButton = true
                    isBottomBarVisible = true
                    isTopBarVisible = true
                    DetailAlumnoScreen(showSnackbar, destination.name)
                }

                composable(DetalleInformeDestination.ROUTE) { backStackEntry ->
                    val informeId = backStackEntry.arguments?.getString("informeId")?.toIntOrNull()
                    if (informeId != null) {
                        topBarTitle = "Detalle del Informe"
                        showBackButton = true
                        isBottomBarVisible = true
                        isTopBarVisible = true
                        DetalleInformeScreen(showSnackbar, informeId = informeId, navController = navController)
                    } else {
                        Text("Error: Informe no encontrado")
                    }
                }


                composable<InformeDestination> {
                    topBarTitle = "Informes"
                    showBackButton = true
                    isBottomBarVisible = true
                    InformesScreen(navController = navController)
                }
            }
        }
    }
