package nl.jovmit.androiddevs.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "about") val about: String
)