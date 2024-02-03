package com.krashkrosh748199.shoption.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.models.Order
import com.krashkrosh748199.shoption.ui.adapters.CartItemsListAdapter
import com.krashkrosh748199.shoption.utils.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class MyOrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order_details)
         setupActionBar()

        var myOrderDetails: Order = Order() // Assuming this default constructor is allowed in your Order class


        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
           myOrderDetails = intent.getParcelableExtra<Order>(Constants.EXTRA_MY_ORDER_DETAILS)!!
        }

        setupUI(myOrderDetails)


    }
    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_my_order_details_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<Toolbar>(R.id.toolbar_my_order_details_activity).setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupUI(orderDetails:Order) {

        findViewById<TextView>(R.id.tv_order_details_id_now).text = orderDetails.title.toString()

        // Assuming orderDetails.order_datetime is in milliseconds
        val orderDateTime = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            .format(orderDetails.order_datetime)

        findViewById<TextView>(R.id.tv_order_details_date).text = orderDateTime


        val diffInMilliSeconds: Long = System.currentTimeMillis() - orderDetails.order_datetime
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds)
        Log.e("Difference in Hours", "$diffInHours")

        when {
            diffInHours < 1 -> {
               findViewById<TextView>(R.id.tv_order_status).text = resources.getString(R.string.order_status_pending)
                findViewById<TextView>(R.id.tv_order_status).setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorAccent
                    )
                )
            }
            diffInHours < 2 -> {
                findViewById<TextView>(R.id.tv_order_status).text = resources.getString(R.string.order_status_in_process)
                findViewById<TextView>(R.id.tv_order_status).setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusInProcess
                    )
                )
            }
            else -> {
                findViewById<TextView>(R.id.tv_order_status).text = resources.getString(R.string.order_status_delivered)
                findViewById<TextView>(R.id.tv_order_status).setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusDelivered
                    )
                )
            }
        }

        findViewById<RecyclerView>(R.id.rv_my_order_items_list).layoutManager = LinearLayoutManager(this@MyOrderDetailsActivity)
        findViewById<RecyclerView>(R.id.rv_my_order_items_list).setHasFixedSize(true)

        val cartListAdapter =
            CartItemsListAdapter(this@MyOrderDetailsActivity, orderDetails.items, false)
        findViewById<RecyclerView>(R.id.rv_my_order_items_list).adapter = cartListAdapter

        findViewById<TextView>(R.id.tv_my_order_details_address_type).text = orderDetails.address?.type
        findViewById<TextView>(R.id.tv_my_order_details_full_name).text = orderDetails.address?.name
        findViewById<TextView>(R.id.tv_my_order_details_address).text =
            "${orderDetails.address?.address}, ${orderDetails.address?.pinCode}"
        findViewById<TextView>(R.id.tv_my_order_details_additional_note).text = orderDetails.address?.additionalNote

        if (orderDetails.address?.otherDetails?.isNotEmpty() == true) {
            findViewById<TextView>(R.id.tv_my_order_details_other_details).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_my_order_details_other_details).text = orderDetails.address.otherDetails
        } else {
            findViewById<TextView>(R.id.tv_my_order_details_other_details).visibility = View.GONE
        }
        findViewById<TextView>(R.id.tv_my_order_details_mobile_number).text = orderDetails.address?.mobileNumber

        findViewById<TextView>(R.id.tv_order_details_sub_total).text = orderDetails.sub_total_amount
        findViewById<TextView>(R.id.tv_order_details_shipping_charge).text = orderDetails.shipping_charge
        findViewById<TextView>(R.id.tv_order_details_total_amount).text = orderDetails.total_amount

    }


}