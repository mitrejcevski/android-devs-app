package nl.jovmit.androiddevs.domain.auth

import nl.jovmit.androiddevs.domain.auth.data.AuthResult

interface AuthRepository {

    suspend fun login(email: String, password: String): AuthResult

    suspend fun signUp(email: String, password: String, about: String): AuthResult
}