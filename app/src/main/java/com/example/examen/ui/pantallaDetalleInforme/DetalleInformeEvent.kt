package com.example.examen.ui.pantallaDetalleInforme

import com.example.examen.data.local.model.Informe

sealed class DetalleInformeEvent {
    data class UpdateInforme(val informe: Informe) : DetalleInformeEvent()
    data class GetInforme(val informeId: Int) : DetalleInformeEvent()
}
