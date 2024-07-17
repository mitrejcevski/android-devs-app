package nl.jovmit.androiddevs.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLogoutSignal(): LogoutSignal {
        return LogoutSignal()
    }

    @Singleton
    @Provides
    fun provideUnauthorizedInterceptor(
        logoutSignal: LogoutSignal
    ): ExpiredTokenInterceptor {
        return ExpiredTokenInterceptor(logoutSignal)
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        expiredAuthInterceptor: ExpiredTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(expiredAuthInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.skool.com/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}