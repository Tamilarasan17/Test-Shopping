package com.test.testshopping.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "food_name") var foodName: String? = "",
    @ColumnInfo(name = "food_price") var foodPrice: Int? = 0,
    @ColumnInfo(name = "quantity") var quantity: Int? = 0,
    @ColumnInfo(name = "is_show") var isShow: Int? = 0
)