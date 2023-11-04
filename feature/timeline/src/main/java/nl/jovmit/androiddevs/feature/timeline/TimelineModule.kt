package nl.jovmit.androiddevs.feature.timeline

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimelineModule {

    @Provides
    @Singleton
    fun provideTimelineRepository(): TimelineRepository {
        val fakeData = (1..150).map { ListItem("$it", "List Item $it") }
        return InMemoryTimelineRepository(fakeData)
    }
}