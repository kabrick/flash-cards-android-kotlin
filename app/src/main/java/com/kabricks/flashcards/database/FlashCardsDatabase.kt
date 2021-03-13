package com.kabricks.flashcards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrentScore::class], version = 1, exportSchema = false)
abstract class FlashCardsDatabase : RoomDatabase() {

    abstract val currentScoreDao: CurrentScoreDao

    // companion allows accessing method without instantiating
    companion object {
        // used to keep a reference to the DB so no need to create
        // multiple instances
        @Volatile
        private var INSTANCE: FlashCardsDatabase? = null

        fun getInstance(context: Context): FlashCardsDatabase {
            // ensure that only one thread access the db with synchronized
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlashCardsDatabase::class.java,
                        "flash_cards_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}