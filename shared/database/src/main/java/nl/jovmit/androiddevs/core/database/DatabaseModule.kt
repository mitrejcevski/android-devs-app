package nl.jovmit.androiddevs.core.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver {
        return AndroidSqliteDriver(UsersDatabase.Schema, context, "users.db")
    }

    @Provides
    @Singleton
    fun provideUsersDatabase(
        driver: SqlDriver
    ): UsersDatabase {
        return UsersDatabase(driver)
    }

    @Provides
    @Singleton
    fun provideUsersQueries(
        database: UsersDatabase
    ): UserEntityQueries {
        return database.userEntityQueries
    }
}