package com.example.examen.data.remote.di


import com.example.examen.BuildConfig
import com.example.examen.data.PreferencesRepository
import com.example.examen.data.local.InformeDao
import com.example.examen.data.remote.services.AlumnoService
import com.example.examen.data.remote.services.LoginService
import com.example.examen.data.remote.services.RatonService
import com.example.examen.data.remote.utils.AuthAuthenticator
import com.example.examen.data.remote.utils.AuthInterceptor
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//esto es para lo que vaya con retrofit, para las cosas de ROOM usar Databasemodule
object NetworkModule {
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideRatService(retrofit: Retrofit): RatonService =
        retrofit.create(RatonService::class.java)

    @Singleton
    @Provides
    fun provideAlumnoService(retrofit: Retrofit): AlumnoService =
        retrofit.create(AlumnoService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
    @Singleton
    @Provides
    fun provideAuthInterceptor(preferencesRepository: PreferencesRepository): AuthInterceptor =
        AuthInterceptor(preferencesRepository)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(
        preferencesRepository: PreferencesRepository,
        loginService: Lazy<LoginService>
    ): AuthAuthenticator =
        AuthAuthenticator(preferencesRepository)
}