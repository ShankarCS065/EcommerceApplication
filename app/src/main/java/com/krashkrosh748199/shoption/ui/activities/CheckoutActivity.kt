package com.krashkrosh748199.shoption.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Address
import com.krashkrosh748199.shoption.models.CartItem
import com.krashkrosh748199.shoption.models.Order
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.ui.adapters.CartItemsListAdapter
import com.krashkrosh748199.shoption.utils.Constants

class CheckoutActivity : BaseActivity() {

    private var mAddressDetails: Address?= null
    private lateinit var mProductsList:ArrayList<Product>
    private lateinit var mCartItemsList:ArrayList<CartItem>
    private var mSubTotal:Double = 0.0
    private var mTotalAmount:Double=0.0
    private lateinit var mOrderDetails:Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupActionBar()

        if(intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)){
            mAddressDetails= intent.getParcelableExtra(Constants.EXTRA_SELECTED_ADDRESS)
        }
        if (mAddressDetails != null) {
            findViewById<TextView>(R.id.tv_checkout_address_type).text = mAddressDetails?.type
            findViewById<TextView>(R.id.tv_checkout_full_name).text = mAddressDetails?.name
            findViewById<TextView>(R.id.tv_checkout_address).text = "${mAddressDetails!!.address}, ${mAddressDetails!!.pinCode}"
            findViewById<TextView>(R.id.tv_checkout_additional_note).text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                findViewById<TextView>(R.id.tv_checkout_other_details).text = mAddressDetails?.otherDetails
            }
            findViewById<TextView>(R.id.tv_checkout_mobile_number).text = mAddressDetails?.mobileNumber
        }
        getProductList()

        findViewById<Button>(R.id.btn_place_order).setOnClickListener {
            placeAnOrder()
        }

    }

    fun allDetailsUpdatedSuccessfully() {

        hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }

    fun orderPlacedSuccess() {
        FireStoreClass().updateAllDetails(this@CheckoutActivity,mCartItemsList,mOrderDetails)
    }

    fun successProductsListFromFireStore(productsList:ArrayList<Product>){

        mProductsList=productsList
        getCartItemsList()
    }

    private fun getCartItemsList(){
        FireStoreClass().getCartList(this@CheckoutActivity)
    }

    private fun placeAnOrder() {

        showProgressDialog(resources.getString(R.string.please_wait))
      if (mAddressDetails!=null) {
          mOrderDetails  = mCartItemsList[0].image?.let {
              Order(
                  FireStoreClass().getCurrentUserID(),
                  mCartItemsList,
                  mAddressDetails!!,
                  "My order ${System.currentTimeMillis()}",
                  it,
                  mSubTotal.toString(),
                  "10.0",
                  mTotalAmount.toString(),
                  System.currentTimeMillis()
              )
          }!!
          FireStoreClass().placeOrder(this@CheckoutActivity, mOrderDetails)
      }

    }




    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()
        for (product in mProductsList) {
            for (cartItem in cartList) {
                if (product.product_id == cartItem.product_id) {
                    cartItem.stock_quantity = product.stock_quantity
                }
            }
        }
        mCartItemsList = cartList

        findViewById<RecyclerView>(R.id.rv_cart_list_items).layoutManager = LinearLayoutManager(this@CheckoutActivity)
        findViewById<RecyclerView>(R.id.rv_cart_list_items).setHasFixedSize(true)

        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
        findViewById<RecyclerView>(R.id.rv_cart_list_items).adapter = cartListAdapter



        for (item in mCartItemsList) {

            val availableQuantity = item.stock_quantity?.toInt()

            if (availableQuantity != null) {
                if (availableQuantity > 0) {
                    val price = item.price?.toDouble()
                    val quantity = item.cart_quantity?.toInt()

                    mSubTotal += (quantity?.let { price?.times(it) }!!)
                }
            }
        }
        findViewById<TextView>(R.id.tv_checkout_sub_total).text = "Rs $mSubTotal"
        findViewById<TextView>(R.id.tv_checkout_shipping_charge).text = "Rs 10.0"

        if (mSubTotal > 0) {
            findViewById<LinearLayout>(R.id.ll_checkout_place_order).visibility = View.VISIBLE

            mTotalAmount = mSubTotal + 10.0
            findViewById<TextView>(R.id.tv_checkout_total_amount).text = "Rs $mTotalAmount"
        } else {
            findViewById<LinearLayout>(R.id.ll_checkout_place_order).visibility = View.GONE
        }

    }

    private fun getProductList(){
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getAllProductsList(this@CheckoutActivity)
    }

    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_checkout_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<Toolbar>(R.id.toolbar_checkout_activity).setNavigationOnClickListener { onBackPressed() }
    }

}