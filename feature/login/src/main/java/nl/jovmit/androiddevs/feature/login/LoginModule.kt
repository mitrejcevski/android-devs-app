package nl.jovmit.androiddevs.feature.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideUserCatalog(): UsersCatalog {
        return InMemoryUsersCatalog(emptyMap())
    }
}