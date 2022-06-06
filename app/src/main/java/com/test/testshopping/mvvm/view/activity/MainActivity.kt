package com.test.testshopping.mvvm.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.testshopping.R
import com.test.testshopping.databinding.ActivityMainBinding
import com.test.testshopping.mvvm.view.adapter.MainListAdapter
import com.test.testshopping.mvvm.viewmodel.AppViewModel
import com.test.testshopping.room.entity.FoodItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainInterface {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainListAdapter by lazy {
        MainListAdapter(this@MainActivity, 0)
    }

    private val appViewModel: AppViewModel by viewModels()

    private var mainList = arrayListOf<FoodItemEntity>()

    private var backFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            toolbar.title = ""
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow)

            mainListView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            mainListView.adapter = adapter

            viewCartLay.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (adapter.getList().isNotEmpty()) {
                        for (item in adapter.getList()) {
                            if (item.quantity != 0) {
                                item.isShow = 1
                            }
                        }
                        appViewModel.update(adapter.getList())
                    }

                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@MainActivity, MyCartActivity::class.java))
                    }
                }
            }
        }

        appViewModel.getData().observe(this@MainActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                mainList.clear()
                mainList.addAll(it)
                Log.i("dragon_test", "size : ${it.size}")
                adapter.setList(mainList)
            }
        })

    }


    override fun onCartCountListener(count: Int) {
        if (count != 0) {
            binding.cartItemText.text = "VIEW CART ($count ITEMS)"
        } else {
            binding.cartItemText.text = "VIEW CART"
        }
    }

    override fun updateCart(id: Int, isShow: Int) {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (backFlag == 0) {
            Toast.makeText(this@MainActivity, "Press again to Exit", Toast.LENGTH_SHORT)
                .show()
            backFlag++
        } else {
            finish()
        }
    }

    override fun onDestroy() {

        if (_binding != null) {
            _binding = null
        }

        super.onDestroy()
    }
}