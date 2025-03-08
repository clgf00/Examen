package com.example.examen.ui.pantallaInformes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.examen.data.remote.model.Informe

@Composable
fun InformesScreen(
    showSnackbar: (String) -> Unit,
    viewModel: InformesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var nuevoInforme by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(InformeEvent.GetInformes)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Lista de Informes", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.informes) { informe ->
                InformeCard(informe = informe) { selectedInforme ->
                    // Navigate to the "detalle informe" screen when a card is clicked
                    navController.navigate("detalleinforme/${selectedInforme.id}")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Input for new Informe
        OutlinedTextField(
            value = nuevoInforme,
            onValueChange = { nuevoInforme = it },
            label = { Text("Nuevo informe") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Add button for new Informe
        Button(
            onClick = {
                if (nuevoInforme.isNotBlank()) {
                    viewModel.handleEvent(
                        InformeEvent.InsertInforme(Informe(id = 0, nombre = nuevoInforme, nivel = (1..3).random().toString()))
                    )
                    nuevoInforme = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Informe")
        }
    }
}

@Composable
fun InformeCard(informe: Informe, onCardClick: (Informe) -> Unit) {
    val backgroundColor = when (informe.nivel) {
        "1" -> MaterialTheme.colorScheme.primary
        "2" -> MaterialTheme.colorScheme.secondary
        "3" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(informe) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = informe.nombre,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nivel: ${informe.nivel}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
