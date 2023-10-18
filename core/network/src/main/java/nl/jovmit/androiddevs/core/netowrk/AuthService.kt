package nl.jovmit.androiddevs.core.netowrk

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/signUp")
    suspend fun signUp(@Body signUpData: SignUpData): AuthResponse

    @POST("/login")
    suspend fun login(@Body loginData: LoginData): AuthResponse
}