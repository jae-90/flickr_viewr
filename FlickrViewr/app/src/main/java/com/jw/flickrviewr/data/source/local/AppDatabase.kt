package com.jw.flickrviewr.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jw.flickrviewr.util.Const.DB_NAME

/**
 * The Room Database that has the SearchTerm table.
 */
@Database(entities = [SearchTerm::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun termDao(): TermDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }

}