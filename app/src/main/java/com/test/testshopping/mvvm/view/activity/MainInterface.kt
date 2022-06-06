package com.test.testshopping.mvvm.view.activity

interface MainInterface {

    fun onCartCountListener(count: Int)

    fun updateCart(id: Int, isShow: Int)
}