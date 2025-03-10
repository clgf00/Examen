package com.example.examen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examen.common.Constantes
import com.example.examen.data.local.model.Informe

@Entity(tableName = Constantes.INFORMES)
data class InformeEntity(

    @PrimaryKey(autoGenerate = true)
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