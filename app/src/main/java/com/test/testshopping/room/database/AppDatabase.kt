package com.test.testshopping.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testshopping.room.dao.AppDao
import com.test.testshopping.room.entity.FoodItemEntity

@Database(entities = [FoodItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val databaseName = "myData.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(LOCK) {
            val instance = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .createFromAsset("database/$databaseName")
                .build()
            INSTANCE = instance
            instance
        }
    }
}