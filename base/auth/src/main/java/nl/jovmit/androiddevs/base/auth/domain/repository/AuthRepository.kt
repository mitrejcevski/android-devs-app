package nl.jovmit.androiddevs.base.auth.domain.repository

import nl.jovmit.androiddevs.base.auth.domain.model.AuthResult

interface AuthRepository {

    suspend fun login(email: String, password: String): AuthResult

    suspend fun signUp(email: String, password: String, about: String): AuthResult
}