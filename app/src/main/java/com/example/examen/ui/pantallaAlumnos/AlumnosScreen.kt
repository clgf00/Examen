package com.example.examen.ui.pantallaAlumnos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.examen.data.remote.model.Alumno
import com.example.examen.ui.common.UiEvent

@Composable
fun AlumnosScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: AlumnosViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(AlumnosEvent.GetAlumnos)
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
                viewModel.handleEvent(AlumnosEvent.AvisoVisto)
            } else if (it is UiEvent.Navigate) {
                onNavigateToDetail(state.selectedName)
                viewModel.handleEvent(AlumnosEvent.AvisoVisto)
            }
        }
    }

    ScreenContent(state = state, onClick = { playerId ->
        viewModel.handleEvent(AlumnosEvent.AlumnoSelected(playerId))
    })
}

@Composable
fun ScreenContent(state: AlumnosState, onClick: (String) -> Unit = {}) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.alumnos.isEmpty() -> {
                    Text(
                        "No hay alumnos",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn {
                        items(state.alumnos) { player ->
                            AlumnosCard(
                                alumno = player,
                                onAClick = { onClick(player.nombre) }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AlumnosCard(alumno: Alumno, onAClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onAClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = alumno.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "apellido =" + alumno.apellido,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}


