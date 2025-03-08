package com.example.examen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examen.data.remote.model.Informe

@Entity(tableName = "informes")
data class InformeEntity(

    @PrimaryKey
    val id: Int,
    val nombre: String,
    val rol: String
)

fun InformeEntity.toInforme(): Informe = Informe(
    id = this.id,
    nombre = this.nombre,
    nivel = this.rol
)

fun Informe.toInformeEntity(): InformeEntity = InformeEntity(
    id = this.id,
    nombre = this.nombre,
    rol = this.nivel
)