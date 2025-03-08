package com.example.examen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InformeDao {
    @Query("SELECT * FROM informes")
    fun getAll(): List<InformeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInforme(informe: InformeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInformes(informes: List<InformeEntity>)
}