package com.test.testshopping.mvvm.repository

import com.test.testshopping.room.dao.AppDao
import com.test.testshopping.room.entity.FoodItemEntity

class AppRepository(private val appDao: AppDao) {

    fun getData() = appDao.getData()

    fun getCart() = appDao.getCart()

    fun update(list: List<FoodItemEntity>) {
        appDao.update(list)
    }

    fun updateQuantity() {
        appDao.updateQuantity()
    }

    fun updateCart(id: Int, isShow: Int) {
        appDao.updateCart(id, isShow)
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null
        private val LOCK = Any()

        fun getInstance(appDao: AppDao): AppRepository = INSTANCE ?: synchronized(LOCK) {
            val instance = AppRepository(appDao)
            INSTANCE = instance
            instance
        }
    }

}