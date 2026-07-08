package nl.jovmit.androiddevs.domain.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

  @Provides
  @Singleton
  internal fun bindAuthRepository(): AuthRepository =
    InMemoryAuthRepository()

  @Provides
  @Singleton
  internal fun bindUserSession(): UserSession =
    InMemoryUserSession()
}
