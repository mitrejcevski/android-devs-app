package nl.jovmit.androiddevs.domain.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(
        repository: RemoteAuthRepository
    ): AuthRepository
}