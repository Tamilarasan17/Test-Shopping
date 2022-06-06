package com.test.testshopping.mvvm.view.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.testshopping.R
import com.test.testshopping.databinding.ActivityMyCartBinding
import com.test.testshopping.mvvm.view.adapter.MainListAdapter
import com.test.testshopping.mvvm.viewmodel.AppViewModel
import com.test.testshopping.room.entity.FoodItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCartActivity : AppCompatActivity(), MainInterface {


    private var _binding: ActivityMyCartBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainListAdapter by lazy {
        MainListAdapter(this@MyCartActivity, 1)
    }

    private val appViewModel: AppViewModel by viewModels()

    private var mainList = arrayListOf<FoodItemEntity>()

    private var showMoreClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            toolbar.title = "My Cart"
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow)

            mainListView.layoutManager = GridLayoutManager(this@MyCartActivity, 1)
            mainListView.adapter = adapter

            placeOrderLay.setOnClickListener {
                if (mainList.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        appViewModel.updateQuantity()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MyCartActivity,
                                "Order Placed Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@MyCartActivity,
                        "Please add items to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



            showMoreCard.setOnClickListener {
                showMoreLay.isVisible = false
                adapter.setList(mainList)
            }
        }


        appViewModel.getCart().observe(this@MyCartActivity, Observer {
            if (it != null && it.isNotEmpty()) {

                binding.mainListView.isVisible = true
                binding.emptyLay.isVisible = false

                mainList.clear()
                mainList.addAll(it)

                binding.totalCost.text = "\u20B9${getAmount(mainList)}"

                Log.i("dragon_test", "size : ${it.size}")

                if (mainList.size <= 2 || showMoreClicked) {
                    adapter.setList(mainList)
                } else {
                    binding.showMoreCard.isVisible = true
                    setUpList(mainList)
                }

            } else {
                binding.mainListView.isVisible = false
                binding.emptyLay.isVisible = true
                binding.totalCost.text = "\u20B90"
                binding.showMoreCard.isVisible = false
            }
        })


    }

    private fun setUpList(list: ArrayList<FoodItemEntity>) {
        val twoList = arrayListOf<FoodItemEntity>()

        for (i in list.indices) {
            if (i > 1) {
                break
            }

            twoList.add(list[i])
        }

        showMoreClicked = true

        adapter.setList(twoList)
    }

    override fun onCartCountListener(count: Int) {
        if (adapter.getList().isNotEmpty()) {
            binding.totalCost.text = "\u20B9${getAmount(adapter.getList())}"
        }
    }

    override fun updateCart(id: Int, isShow: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            appViewModel.updateCart(id, isShow)
        }
    }

    private fun getAmount(list: ArrayList<FoodItemEntity>): Int {

        var sum = 0

        for (item in list) {

            if (item.isShow == 1) {
                sum += (item.quantity?.times(item.foodPrice!!)!!)
            }

        }

        return sum
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        lifecycleScope.launch(Dispatchers.IO) {

            if (adapter.getList().isNotEmpty()) {
                appViewModel.update(adapter.getList())
            }

            withContext(Dispatchers.IO) {
                finish()
            }

        }
    }

    override fun onDestroy() {


        if (_binding != null) {
            _binding = null
        }

        super.onDestroy()
    }
}