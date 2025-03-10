package com.example.examen.ui.pantallaAlumnos

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
class AlumnosViewModel @Inject constructor(private val getAlumnos: GetAlumnos) : ViewModel() {
    private val _uiState = MutableStateFlow(AlumnosState())
    val uiState = _uiState.asStateFlow()
fun handleEvent (event : AlumnosEvent){
    when(event){
        is AlumnosEvent.AvisoVisto -> avisoVisto()
        is AlumnosEvent.AlumnoSelected -> selectAlumno(event.name)
        is AlumnosEvent.GetAlumnos -> getAlumnos()
    }
}
    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getAlumnos(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getAlumnos.invoke()) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(alumnos = result.data, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            alumnos = emptyList(),
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun selectAlumno(name: String) {
        _uiState.update {
            it.copy(
                selectedName = name,
                aviso = UiEvent.Navigate
            )
        }
    }
}