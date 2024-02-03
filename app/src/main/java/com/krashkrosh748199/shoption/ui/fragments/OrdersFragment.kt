package com.krashkrosh748199.shoption.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Order
import com.krashkrosh748199.shoption.ui.adapters.MyOrdersListAdapter


class OrdersFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        return root


    }

    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }

    private fun getMyOrdersList() {

        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getMyOrdersList(this@OrdersFragment)
    }

    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        hideProgressDialog()

        val myOrderItems= view?.findViewById<RecyclerView>(R.id.rv_my_order_items)
        val noOrdersFound= view?.findViewById<TextView>(R.id.tv_no_orders_found)
        if (ordersList.size > 0) {

           myOrderItems?.visibility = View.VISIBLE
            noOrdersFound?.visibility = View.GONE

            myOrderItems?.layoutManager = LinearLayoutManager(activity)
            myOrderItems?.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
            myOrderItems?.adapter = myOrdersAdapter
        } else {
            myOrderItems?.visibility = View.GONE
            noOrdersFound?.visibility = View.VISIBLE
        }


    }


}