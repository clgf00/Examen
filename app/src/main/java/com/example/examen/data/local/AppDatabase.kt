package com.example.examen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [InformeEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun informeDao(): InformeDao

}