package com.example.examen.ui.pantallaInformes

import com.example.examen.data.remote.model.Informe

sealed class InformeEvent {
    data object GetInformes : InformeEvent()
    data class InsertInforme(val informe: Informe) : InformeEvent()
    data object Error : InformeEvent()

}