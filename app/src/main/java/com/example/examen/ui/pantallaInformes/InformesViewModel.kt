package com.example.examen.ui.pantallaInformes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.model.Informe
import com.example.examen.di.IoDispatcher
import com.example.examen.domain.usecases.AddInforme
import com.example.examen.domain.usecases.GetInformes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InformesViewModel @Inject constructor(
    private val getInformesUseCase: GetInformes,
    private val insertInformeUseCase: AddInforme,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {


    private val _state = MutableStateFlow(InformesState())
    val state: StateFlow<InformesState> = _state.asStateFlow()

    fun handleEvent(event: InformeEvent) {
        when (event) {
            is InformeEvent.GetInformes -> getInformes()
            is InformeEvent.Error -> {
                _state.update { it.copy(error = null) }
            }

            is InformeEvent.InsertInforme -> insertInforme(event.informe)
        }
    }

    init {
        getInformes()
    }

    private fun getInformes() {
        viewModelScope.launch {
            getInformesUseCase().collect { result: NetworkResult<List<Informe>> ->
                _state.update {
                    when (result) {
                        is NetworkResult.Error -> it.copy(error = result.message, isLoading = false)
                        is NetworkResult.Success -> it.copy(
                            informes = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun insertInforme(informe: Informe) {
        viewModelScope.launch {
            insertInformeUseCase(informe)
        }
    }
}