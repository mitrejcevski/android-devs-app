package nl.jovmit.androiddevs.domain.timeline

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimelineModule {

    @Binds
    @Singleton
    internal abstract fun bindTimelineRepository(
        repository: InMemoryTimelineRepository
    ): TimelineRepository
}
