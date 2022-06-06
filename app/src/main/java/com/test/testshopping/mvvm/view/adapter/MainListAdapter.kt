package com.test.testshopping.mvvm.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.test.testshopping.databinding.ListItemLayBinding
import com.test.testshopping.mvvm.view.activity.MainInterface
import com.test.testshopping.room.entity.FoodItemEntity

class MainListAdapter(private val mContext: Context, private val viewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mainList = arrayListOf<FoodItemEntity>()

    private val mainInterface = mContext as MainInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            0 -> {
                MyViewHolder(
                    ListItemLayBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }
            else -> {
                MyViewHolderTwo(
                    ListItemLayBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.bind(position)
        } else if (holder is MyViewHolderTwo) {
            holder.bind(position)
        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<FoodItemEntity>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ListItemLayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Log.i("dragon_test", "hello $position")
            binding.run {
                foodName.text = mainList[position].foodName
                foodPrice.text = "\u20B9${mainList[position].foodPrice}"

                if (mainList[position].quantity == 0) {
                    addCard.isVisible = true
                    addQuantityLay.isVisible = false
                } else {
                    addCard.isVisible = false
                    addQuantityLay.isVisible = true

                    quantity.text = "${mainList[position].quantity}"
                }

                addCard.setOnClickListener {
                    addCard.isVisible = false
                    addQuantityLay.isVisible = true
                    quantity.text = "1"
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }

                minusView.setOnClickListener {
                    if (quantity.text.toString() == "1") {
                        addCard.isVisible = true
                        addQuantityLay.isVisible = false
                        quantity.text = "0"
                    } else {
                        val minus = quantity.text.toString().toInt()
                        quantity.text = "${minus - 1}"
                    }
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }

                plusView.setOnClickListener {
                    if (quantity.text.toString() == "20") {
                        Toast.makeText(
                            mContext,
                            "Only 20 Items are allowed to add",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val plus = quantity.text.toString().toInt()
                        quantity.text = "${plus + 1}"
                    }
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }
            }
        }

        private fun updateCartCount() {
            var sum = 0

            for (item in mainList) {
                sum += item.quantity!!
            }

            mainInterface.onCartCountListener(sum)
        }
    }

    inner class MyViewHolderTwo(private val binding: ListItemLayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Log.i("dragon_test", "hello $position")
            binding.run {
                foodName.text = mainList[position].foodName
                foodPrice.text = "\u20B9${mainList[position].foodPrice}"

                if (mainList[position].quantity == 0) {
                    addCard.isVisible = true
                    addQuantityLay.isVisible = false
                } else {
                    addCard.isVisible = false
                    addQuantityLay.isVisible = true

                    quantity.text = "${mainList[position].quantity}"
                }

                addCard.setOnClickListener {
                    addCard.isVisible = false
                    addQuantityLay.isVisible = true
                    quantity.text = "1"
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }

                minusView.setOnClickListener {
                    if (quantity.text.toString() == "1") {
                        addCard.isVisible = true
                        addQuantityLay.isVisible = false
                        quantity.text = "0"
                        mainInterface.updateCart(mainList[position].id, 0)
                    } else {
                        val minus = quantity.text.toString().toInt()
                        quantity.text = "${minus - 1}"
                    }
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }

                plusView.setOnClickListener {
                    if (quantity.text.toString() == "20") {
                        Toast.makeText(
                            mContext,
                            "Only 20 Items are allowed to add",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val plus = quantity.text.toString().toInt()
                        quantity.text = "${plus + 1}"
                    }
                    mainList[position].quantity = quantity.text.toString().toInt()
                    updateCartCount()
                }
            }
        }

        private fun updateCartCount() {
            var sum = 0

            for (item in mainList) {
                sum += item.quantity!!
            }

            mainInterface.onCartCountListener(sum)
        }
    }

    fun getList() = mainList
}
