package com.krashkrosh748199.shoption.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.CartItem
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader

class ProductDetailsActivity : BaseActivity(),View.OnClickListener {
    private var mProductId:String=""
    private lateinit var mProductDetails:Product
    private  var mProductOwnerId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        setupActionBar()
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)){
            mProductId=intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!

        }
        //var productOwnerId:String=""
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)){
            mProductOwnerId=intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!

        }
        if(FireStoreClass().getCurrentUserID()==mProductOwnerId){
            findViewById<Button>(R.id.btn_add_to_cart).visibility=View.GONE
            findViewById<Button>(R.id.btn_go_to_cart).visibility=View.GONE
        }
        else{
            findViewById<Button>(R.id.btn_add_to_cart).visibility=View.VISIBLE
        }

        getProductDetails()

        findViewById<Button>(R.id.btn_add_to_cart).setOnClickListener(this)
        findViewById<Button>(R.id.btn_go_to_cart).setOnClickListener(this)
    }
    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_product_details_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<Toolbar>(R.id.toolbar_product_details_activity).setNavigationOnClickListener { onBackPressed() }
    }
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FireStoreClass to get the product details.
        FireStoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
    }

    fun productDetailsSuccess(product: Product) {
        mProductDetails=product

        // Hide Progress dialog.
       // hideProgressDialog()

        // Populate the product details in the UI.
        product.image?.let {
            GlideLoader(this@ProductDetailsActivity).loadProductPicture(
                it,
                findViewById(R.id.iv_product_detail_image)
            )
        }

        findViewById<TextView>(R.id.tv_product_details_title).text = product.title
        findViewById<TextView>(R.id.tv_product_details_price).text = "Rs ${product.price}"
        findViewById<TextView>(R.id.tv_product_details_description).text = product.description
        findViewById<TextView>(R.id.tv_product_details_available_quantity).text = product.stock_quantity

        if (product.stock_quantity?.toInt()==0){
            hideProgressDialog()
            findViewById<Button>(R.id.btn_add_to_cart).visibility=View.GONE

            findViewById<TextView>(R.id.tv_product_details_available_quantity).text=
                resources.getString(R.string.lbl_out_of_stock)

            findViewById<TextView>(R.id.tv_product_details_available_quantity).setTextColor(
                ContextCompat.getColor(
                    this@ProductDetailsActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            if (FireStoreClass().getCurrentUserID()==product.user_id){
                hideProgressDialog()
            }
            else{
                FireStoreClass().checkIfItemExistInCart(this,mProductId)
            }
        }


    }


    private fun addToCart() {

        val  cartItem = CartItem(
            FireStoreClass().getCurrentUserID(),
            mProductOwnerId,
            mProductId,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().addCartItems(this,cartItem)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_add_to_cart -> {

                    addToCart()
                }
                R.id.btn_go_to_cart->{
                   startActivity(Intent(this@ProductDetailsActivity,CartListActivity::class.java))
                }
            }

        }

    }

    fun addToCartSuccess() {
        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailsActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        findViewById<Button>(R.id.btn_add_to_cart).visibility=View.GONE
        findViewById<Button>(R.id.btn_go_to_cart).visibility=View.VISIBLE
    }

    fun productExistsInCart(){
        hideProgressDialog()
        findViewById<Button>(R.id.btn_add_to_cart).visibility=View.GONE
        findViewById<Button>(R.id.btn_go_to_cart).visibility=View.VISIBLE
    }


}