package com.example.andlrcv

data class Order(
    val userName: String,
    val status: String,
    val orderCode: String,
    val detail: String,
    val date: String,
    val profileImageRes: Int
){
    companion object {
        const val STATUS_DANGCHOT = "Đang chốt"
        const val STATUS_DACHOT = "Đã chốt"
    }
}

fun generateFakeOrders(): List<Order> {
    val orders = mutableListOf<Order>()
    for (i in 1..100) {
        orders.add(
            Order(
                userName = "Nguyen Sharan $i",
                status = if (i % 2 == 0) "Đã chốt" else "Đang chốt",
                orderCode = "8436***03$i",
                detail = "1 đơn mỹ phẩm",
                date = "$i ngày trước",
                profileImageRes = R.drawable.img // Thay bằng URL hoặc tài nguyên nội bộ
            )
        )
    }
    return orders
}

