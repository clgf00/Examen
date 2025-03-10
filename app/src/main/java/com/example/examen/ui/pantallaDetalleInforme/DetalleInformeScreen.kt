package com.example.examen.ui.pantallaDetalleInforme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.examen.ui.navigation.InformeDestination

@Composable
fun DetalleInformeScreen(
    showSnackbar: (String) -> Unit,
    informeId: Int,
    navController: NavController,
    viewModel: DetalleInformeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var nuevoNivel by remember { mutableStateOf("") }

    LaunchedEffect(informeId) {
        viewModel.handleEvent(DetalleInformeEvent.GetInforme(informeId))
    }

    LaunchedEffect(state.informe) {
        state.informe?.let {
            nuevoNivel = it.nivel
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            showSnackbar(it)
        }
    }

    DetalleInformeContent(
        state = state,
        nuevoNivel = nuevoNivel,
        onNivelChange = { nuevoNivel = it },
        onUpdateClick = {
            state.informe?.let {
                viewModel.handleEvent(
                    DetalleInformeEvent.UpdateInforme(it.copy(nivel = nuevoNivel))
                )
                showSnackbar("Informe actualizado")
                navController.navigate(InformeDestination) {
                    popUpTo(InformeDestination) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    )

    if (state.isLoading) {
        CircularProgressIndicator()
    }
}

@Composable
fun DetalleInformeContent(
    state: DetalleInformeState,
    nuevoNivel: String,
    onNivelChange: (String) -> Unit,
    onUpdateClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.informe?.let { informe ->
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Informe: ${informe.nombre}", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nuevoNivel,
                            onValueChange = onNivelChange,
                            label = { Text("Nivel del informe") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onUpdateClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Actualizar Nivel")
                        }
                    }
                }
            }
        }
    }
}
