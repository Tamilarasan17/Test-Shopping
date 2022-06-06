package com.test.testshopping.room.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.testshopping.room.entity.FoodItemEntity

@Dao
interface AppDao {

    @Query("select * from food_table")
    fun getData(): LiveData<List<FoodItemEntity>>

    @Query("select * from food_table where is_show=1 and quantity>0")
    fun getCart(): LiveData<List<FoodItemEntity>>

    @Query("update food_table set quantity=0")
    fun updateQuantity()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(list: List<FoodItemEntity>)

    @Query("update food_table set is_show=:isShow where id=:id")
    fun updateCart(id: Int, isShow: Int)
}