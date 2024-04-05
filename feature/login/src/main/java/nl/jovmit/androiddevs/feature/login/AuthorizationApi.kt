package nl.jovmit.androiddevs.feature.login

import nl.jovmit.androiddevs.feature.login.data.LoginData
import nl.jovmit.androiddevs.feature.login.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse
}