package com.example.andlrcv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private var orderList: MutableList<Order>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (orderList[position].status == Order.STATUS_DANGCHOT) {
            VIEW_TYPE_DANGCHOT
        } else {
            VIEW_TYPE_DACHOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DANGCHOT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item_dangchot, parent, false)
            ProcessingViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item_dachot, parent, false)
            CompletedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val order = orderList[position]
        if (holder is ProcessingViewHolder) {
            holder.bind(order)
        } else if (holder is CompletedViewHolder) {
            holder.bind(order)
        }
    }

    override fun getItemCount() = orderList.size

    fun updateData(newOrderList: List<Order>) {
        orderList.clear()
        orderList.addAll(newOrderList)
        notifyDataSetChanged()
    }

    fun addOrders(newOrders: List<Order>) {
        val previousSize = orderList.size
        orderList.addAll(newOrders)
        notifyItemRangeInserted(previousSize, newOrders.size)
    }


    companion object {
        private const val VIEW_TYPE_DANGCHOT = 1
        private const val VIEW_TYPE_DACHOT = 2
    }

    class ProcessingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        private val tvOrderCode: TextView = itemView.findViewById(R.id.tvOrderCode)
        private val tvOrderDetail: TextView = itemView.findViewById(R.id.tvOrderDetail)
        private val tvOrderDate: TextView = itemView.findViewById(R.id.tvOrderDate)
        private val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)

        fun bind(order: Order) {
            tvUserName.text = order.userName
            tvOrderStatus.text = order.status
            tvOrderCode.text = order.orderCode
            tvOrderDetail.text = order.detail
            tvOrderDate.text = order.date
            imgProfile.setImageResource(order.profileImageRes)
        }
    }

    class CompletedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        private val tvOrderCode: TextView = itemView.findViewById(R.id.tvOrderCode)
        private val tvOrderDetail: TextView = itemView.findViewById(R.id.tvOrderDetail)
        private val tvOrderDate: TextView = itemView.findViewById(R.id.tvOrderDate)
        private val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)

        fun bind(order: Order) {
            tvUserName.text = order.userName
            tvOrderStatus.text = order.status
            tvOrderCode.text = order.orderCode
            tvOrderDetail.text = order.detail
            tvOrderDate.text = order.date
            imgProfile.setImageResource(order.profileImageRes)
        }
    }
}



