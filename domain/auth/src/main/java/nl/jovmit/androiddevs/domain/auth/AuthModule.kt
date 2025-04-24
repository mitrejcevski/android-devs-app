package nl.jovmit.androiddevs.domain.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(
        repository: DummyAuthRepo
    ): AuthRepository

    class DummyAuthRepo @Inject constructor() : AuthRepository {
        override suspend fun login(email: String, password: String): AuthResult {
            return AuthResult.Success("token", User("userId", email, "about"))
        }
        override suspend fun signUp(email: String, password: String, about: String): AuthResult {
            TODO("Not yet implemented")
        }
    }
}