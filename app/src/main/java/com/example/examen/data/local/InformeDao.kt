package com.example.examen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.examen.common.Constantes

@Dao
interface InformeDao {
    @Query(Constantes.QUERY_INFORMES)
    fun getAll(): List<InformeEntity>

    @Query(Constantes.QUERY_INFORMES2)
    suspend fun getInformeById(informeId: Int): InformeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInforme(informe: InformeEntity)

    @Update
    suspend fun updateInforme(informe: InformeEntity)
}