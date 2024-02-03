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
import com.krashkrosh748199.shoption.models.CartItem
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.ui.adapters.CartItemsListAdapter
import com.krashkrosh748199.shoption.utils.Constants

class CartListActivity : BaseActivity() {

    private lateinit var mProductsList:ArrayList<Product>
    private lateinit var mCartListItems:ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        setupActionBar()

        findViewById<Button>(R.id.btn_checkout).setOnClickListener{
            val intent = Intent(this@CartListActivity, AddressListActivity::class.java)
            intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, true)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()

        //getCartItemsList()
        getProductList()
    }
    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_cart_list_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<Toolbar>(R.id.toolbar_cart_list_activity).setNavigationOnClickListener { onBackPressed() }
    }
    private fun getCartItemsList() {

        // Show the progress dialog.
        //showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getCartList(this@CartListActivity)
    }
    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        // Hide progress dialog.
        hideProgressDialog()

        for(product in mProductsList){
            for(cartItem in cartList){
                if(product.product_id==cartItem.product_id){
                    cartItem.stock_quantity=product.stock_quantity
                    if(product.stock_quantity?.toInt() == 0){
                        cartItem.cart_quantity=product.stock_quantity
                    }
                }
            }
        }

        mCartListItems=cartList

        if (mCartListItems.size > 0) {

            findViewById<RecyclerView>(R.id.rv_cart_items_list).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.ll_checkout).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_no_cart_item_found).visibility = View.GONE

            findViewById<RecyclerView>(R.id.rv_cart_items_list).layoutManager = LinearLayoutManager(this@CartListActivity)
            findViewById<RecyclerView>(R.id.rv_cart_items_list).setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity, mCartListItems,true)
            findViewById<RecyclerView>(R.id.rv_cart_items_list).adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in mCartListItems) {
                val availableQuantity= item.stock_quantity?.toInt()
                if (availableQuantity != null) {
                    if(availableQuantity > 0){
                        val price = item.price?.toDouble()
                        val quantity = item.cart_quantity?.toInt()

                        subTotal += (price?.times(quantity!!)!!)
                    }
                }

            }

            findViewById<TextView>(R.id.tv_sub_total).text = "Rs $subTotal"
            // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
            findViewById<TextView>(R.id.tv_shipping_charge).text = "Rs 10.0"

            if (subTotal > 0) {
                findViewById<LinearLayout>(R.id.ll_checkout).visibility = View.VISIBLE

                val total = subTotal + 10
                findViewById<TextView>(R.id.tv_total_amount).text = "Rs $total"
            } else {
                findViewById<LinearLayout>(R.id.ll_checkout).visibility = View.GONE
            }

        } else {
            findViewById<RecyclerView>(R.id.rv_cart_items_list).visibility = View.GONE
            findViewById<LinearLayout>(R.id.ll_checkout).visibility = View.GONE
            findViewById<TextView>(R.id.tv_no_cart_item_found).visibility = View.VISIBLE
        }


    }

    fun successProductsListFromFireStore(productsList:ArrayList<Product>){

        hideProgressDialog()
        mProductsList=productsList
        getCartItemsList()
    }

    private fun getProductList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getAllProductsList(this)
    }

    fun itemRemovedSuccess(){
        hideProgressDialog()
        Toast.makeText(this@CartListActivity,
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getCartItemsList()

    }

    fun itemUpdateSuccess(){
        hideProgressDialog()
        getCartItemsList()

    }




}