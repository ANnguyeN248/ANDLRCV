package com.example.andlrcv

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var orderList = mutableListOf<Order>()
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioTatca: RadioButton
    private lateinit var radioDangchot: RadioButton
    private lateinit var radioDachot: RadioButton
    private var currentPage = 0
    private val ITEMS_PER_PAGE = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        radioGroup = findViewById(R.id.radioGroup)
        radioTatca = findViewById(R.id.radioTatca)
        radioDangchot = findViewById(R.id.radioDangchot)
        radioDachot = findViewById(R.id.radioDachot)

        orderList.addAll(generateFakeOrders())
        adapter = OrderAdapter(orderList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            refreshOrders()
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            currentPage = 0
            when (checkedId) {
                R.id.radioTatca -> showAllOrders()
                R.id.radioDangchot -> filterOrders(Order.STATUS_DANGCHOT)
                R.id.radioDachot -> filterOrders(Order.STATUS_DACHOT)
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == orderList.size - 1) {
                    loadMoreOrders()
                }
            }
        })


    }

    private fun refreshOrders() {
        // Giả lập làm mới dữ liệu
        orderList.clear()
        orderList.addAll(generateFakeOrders())
        currentPage = 0
        showAllOrders()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadMoreOrders() {
        currentPage++
        val startIndex = currentPage * ITEMS_PER_PAGE
        val endIndex = (currentPage + 1) * ITEMS_PER_PAGE

        if (startIndex < orderList.size) {
            // Lấy danh sách con và chuyển thành MutableList
            val newOrders = orderList.subList(startIndex, endIndex.coerceAtMost(orderList.size)).toMutableList()
            adapter.addOrders(newOrders)
        }

    }
    private fun showAllOrders() {
        val paginatedList = orderList.take(ITEMS_PER_PAGE)
        adapter.updateData(paginatedList)
    }

    private fun filterOrders(status: String) {
        val filteredList = orderList.filter { it.status == status }
        val paginatedList = filteredList.take(ITEMS_PER_PAGE)
        adapter.updateData(paginatedList)
    }
}


