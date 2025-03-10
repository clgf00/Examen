package com.example.examen.ui.pantallaDetalleInforme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen.common.Constantes
import com.example.examen.data.local.model.Informe
import com.example.examen.domain.usecases.GetInforme
import com.example.examen.domain.usecases.UpdateInforme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleInformeViewModel @Inject constructor(
    private val getInformeUseCase: GetInforme,
    private val updateInformeUseCase: UpdateInforme
) : ViewModel() {
    private val _state = MutableStateFlow(DetalleInformeState())
    val state: StateFlow<DetalleInformeState> = _state.asStateFlow()

    fun handleEvent(event: DetalleInformeEvent) {
        when (event) {
            is DetalleInformeEvent.GetInforme -> getInforme(event.informeId)
            is DetalleInformeEvent.UpdateInforme -> saveInforme(event.informe)
        }
    }

    private fun getInforme(informeId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val informe = getInformeUseCase(informeId)
                _state.value = _state.value.copy(informe = informe, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = Constantes.INFORME_ERROR)
            }
        }
    }

    private fun saveInforme(informe: Informe) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                updateInformeUseCase(informe)
                _state.value = _state.value.copy(isLoading = false, informe = informe)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = Constantes.ACTUALIZAR_ERROR)
            }
        }
    }
}

