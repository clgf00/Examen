package com.example.examen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface InformeDao {
    @Query("SELECT * FROM informes")
    fun getAll(): List<InformeEntity>

    @Query("SELECT * FROM informes WHERE id = :informeId")
    suspend fun getInformeById(informeId: Int): InformeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInforme(informe: InformeEntity)

    @Update
    suspend fun updateInforme(informe: InformeEntity)
}