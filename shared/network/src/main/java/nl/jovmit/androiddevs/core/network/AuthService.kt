package nl.jovmit.androiddevs.core.network

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/signUp")
    suspend fun signUp(@Body signUpData: SignUpData): AuthResponse

    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData): AuthResponse
}