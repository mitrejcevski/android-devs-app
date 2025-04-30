package nl.jovmit.androiddevs.shared.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object DispatcherModule {

    @Provides
    @ViewModelScoped
    fun provideBackgroundDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}