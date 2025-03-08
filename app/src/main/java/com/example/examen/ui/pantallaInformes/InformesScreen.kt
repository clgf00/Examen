package com.example.examen.ui.pantallaInformes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.examen.data.remote.model.Informe

@Composable
fun InformesScreen(
    showSnackbar: (String) -> Unit,
    viewModel: InformesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(InformeEvent.GetInformes)
    }

    ScreenContent(
        state = state,
        onInsertInforme = {
            viewModel.handleEvent(InformeEvent.InsertInforme(getSampleInforme()))
        }
    )
}

@Composable
fun ScreenContent(
    state: InformesState,
    onInsertInforme: () -> Unit
) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.informes.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No hay informes")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onInsertInforme) {
                            Text("Cargar informes")
                        }
                    }
                }

                else -> {
                    LazyColumn {
                        items(state.informes) { informe ->
                            InformeCard(informe)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InformeCard(informe: Informe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = informe.nombre,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nivel = ${informe.nivel}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun getSampleInforme(): Informe {
    return Informe(id = 0, nombre = "Informe Ejemplo", nivel = (1..3).random().toString())
}
