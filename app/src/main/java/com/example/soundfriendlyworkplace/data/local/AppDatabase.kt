package com.example.soundfriendlyworkplace.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SectorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sectorDao(): SectorDao
}


