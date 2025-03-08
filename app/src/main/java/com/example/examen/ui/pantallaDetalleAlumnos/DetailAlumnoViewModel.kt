package com.example.examen.ui.pantallaDetalleAlumnos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen.data.remote.NetworkResult
import com.example.examen.domain.usecases.GetAlumnos
import com.example.examen.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAlumnoViewModel @Inject constructor(private val getAlumnos: GetAlumnos) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailAlumnoState())
    val uiState = _uiState.asStateFlow()
    fun handleEvent(event: DetailAlumnoEvent) {
        when (event) {
            is DetailAlumnoEvent.AvisoVisto -> avisoVisto()
            is DetailAlumnoEvent.GetAlumno -> getAlumno(event.name)
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getAlumno(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getAlumnos.invoke()) {
                is NetworkResult.Success -> {
                    val record = result.data.find { it.nombre == name }
                    record?.let {
                        _uiState.update { it.copy(a = record, isLoading = false) }
                    }

                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}