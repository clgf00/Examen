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
import androidx.compose.foundation.lazy.LazyColumn
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
    alumno: String,
    viewModel: DetalleAlumnoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(alumno) {
        viewModel.handleEvent(DetalleAlumnoEvent.GetAlumno(alumno))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
                viewModel.handleEvent(DetalleAlumnoEvent.AvisoVisto)
            }
        }
    }
    ADetailScreenContent(state = state)
}
@Composable
fun ADetailScreenContent(state: DetalleAlumnoState) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                val asignaturas = state.alumno.asignaturas ?: emptyList()

                if (asignaturas.isEmpty()) {
                    Text("No hay asignaturas guardadas")
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LazyRow {
                            items(asignaturas) { ass ->
                                MiLazy(ass)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MiLazy(subject : Asignatura) {
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
                    text = subject.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Nota = " + subject.nota,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
