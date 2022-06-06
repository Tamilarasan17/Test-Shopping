package com.test.testshopping.application

import android.app.Application
import com.test.testshopping.room.database.AppDatabase

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(this@MainApplication)

    }
}