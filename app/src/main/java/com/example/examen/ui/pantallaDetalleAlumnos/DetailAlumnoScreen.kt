package com.example.examen.ui.pantallaDetalleAlumnos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.examen.data.remote.model.Asignatura
import com.example.examen.ui.common.UiEvent

@Composable
fun DetailAlumnoScreen(
    showSnackbar: (String) -> Unit,
    player: String,
    viewModel: DetailAlumnoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(player) {
        viewModel.handleEvent(DetailAlumnoEvent.GetAlumno(player))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
                viewModel.handleEvent(DetailAlumnoEvent.AvisoVisto)
            }
        }
    }
    ADetailScreenContent(state = state)
}

@Composable
fun ADetailScreenContent(state: DetailAlumnoState) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.a.asignaturas.isEmpty()) {
                Text("No data asignaturas")
            }else
             {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LazyRow {
                        items(state.a.asignaturas){ ass ->
                            MiLazy(ass)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiLazy(ass : Asignatura) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            ,
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
                    text = ass.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "nota =" + ass.nota,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
