package com.example.examen.ui.pantallaDetalleInforme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetalleInformeScreen(
    showSnackbar: (String) -> Unit,
    informeId: Int,
    viewModel: DetalleInformeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(informeId) {
        viewModel.handleEvent(DetalleInformeEvent.GetInforme(informeId))
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            showSnackbar(it)
        }
    }

    DetalleInformeContent(state)
}

@Composable
fun DetalleInformeContent(state: DetalleInformeState) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.informe?.let { informe ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Informe: ${informe.nombre}", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nivel: ${informe.nivel}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
