package nl.jovmit.androiddevs.base.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.jovmit.androiddevs.base.auth.data.repository.InMemoryAuthRepo
import nl.jovmit.androiddevs.base.auth.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(
        repository: InMemoryAuthRepo //Supply RemoteAuthRepository to talk to backend
    ): AuthRepository
}