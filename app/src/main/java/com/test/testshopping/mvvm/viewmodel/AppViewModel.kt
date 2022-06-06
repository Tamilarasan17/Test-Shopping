package com.test.testshopping.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.test.testshopping.mvvm.repository.AppRepository
import com.test.testshopping.room.database.AppDatabase
import com.test.testshopping.room.entity.FoodItemEntity

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository: AppRepository

    init {
        val appDao = AppDatabase.getInstance(application).appDao()
        appRepository = AppRepository.getInstance(appDao)
    }

    fun getData() = appRepository.getData()

    fun getCart() = appRepository.getCart()

    fun update(list: List<FoodItemEntity>) {
        appRepository.update(list)
    }

    fun updateQuantity() {
        appRepository.updateQuantity()
    }

    fun updateCart(id: Int, isShow: Int) {
        appRepository.updateCart(id, isShow)
    }
}