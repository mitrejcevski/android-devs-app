package nl.jovmit.androiddevs.domain.auth.domain.repository

import nl.jovmit.androiddevs.domain.auth.domain.model.AuthResult

interface AuthRepository {

    suspend fun login(email: String, password: String): AuthResult

    suspend fun signUp(email: String, password: String, about: String): AuthResult
}