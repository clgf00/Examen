package com.example.examen.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.examen.common.Constantes
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
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constantes.APPDB
        )   .createFromAsset(Constantes.INFORMES_RUTA)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideInformeDao(appDatabase: AppDatabase): InformeDao {
        return appDatabase.informeDao()
    }
}