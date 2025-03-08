package com.example.examen.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.examen.data.local.AppDatabase
import com.example.examen.data.local.InformeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    //USAR ESTO Y APPDATABASE PARA LA BASE DE DATOS LOCAL EN ROOM.
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        )   .createFromAsset("database/informes.db")
            .fallbackToDestructiveMigration()
            .build()
    }       //poner el projecto en modo "Project" en vez de "Android" para hacer las carpetas assets/database/informes.db

    @Provides
    fun provideInformeDao(appDatabase: AppDatabase): InformeDao {
        return appDatabase.informeDao()
    }
}