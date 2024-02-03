package com.krashkrosh748199.shoption.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.models.SoldProduct
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SoldProductDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sold_product_details)


        var productDetails: SoldProduct = SoldProduct()

        if (intent.hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)) {
            productDetails =
                intent.getParcelableExtra<SoldProduct>(Constants.EXTRA_SOLD_PRODUCT_DETAILS)!!
        }
        setupActionBar()
        setupUI(productDetails)

    }
    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_sold_product_details_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<Toolbar>(R.id.toolbar_sold_product_details_activity).setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupUI(productDetails: SoldProduct) {

        findViewById<TextView>(R.id.tv_sold_product_details_id).text = productDetails.order_id

        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = productDetails.order_date
        findViewById<TextView>(R.id.tv_sold_product_details_date).text = formatter.format(calendar.time)

        GlideLoader(this@SoldProductDetailsActivity).loadProductPicture(
            productDetails.image,
            findViewById(R.id.iv_product_item_image)
        )
        findViewById<TextView>(R.id.tv_product_item_name).text = productDetails.title
        findViewById<TextView>(R.id.tv_product_item_price).text ="Rs ${productDetails.price}"
        findViewById<TextView>(R.id.tv_sold_product_quantity).text = productDetails.sold_quantity

        findViewById<TextView>(R.id.tv_sold_details_address_type).text = productDetails.address.type
        findViewById<TextView>(R.id.tv_sold_details_full_name).text = productDetails.address.name
        findViewById<TextView>(R.id.tv_sold_details_address).text =
            "${productDetails.address.address}, ${productDetails.address.pinCode}"
        findViewById<TextView>(R.id.tv_sold_details_additional_note).text = productDetails.address.additionalNote

        if (productDetails.address.otherDetails!!.isNotEmpty()) {
            findViewById<TextView>(R.id.tv_sold_details_other_details).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_sold_details_other_details).text = productDetails.address.otherDetails
        } else {
            findViewById<TextView>(R.id.tv_sold_details_other_details).visibility = View.GONE
        }
        findViewById<TextView>(R.id.tv_sold_details_mobile_number).text = productDetails.address.mobileNumber

        findViewById<TextView>(R.id.tv_sold_product_sub_total).text = productDetails.sub_total_amount
        findViewById<TextView>(R.id.tv_sold_product_shipping_charge).text = productDetails.shipping_charge
        findViewById<TextView>(R.id.tv_sold_product_total_amount).text = productDetails.total_amount
    }

}
