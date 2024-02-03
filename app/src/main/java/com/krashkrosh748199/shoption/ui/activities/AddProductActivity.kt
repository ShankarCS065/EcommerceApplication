package com.krashkrosh748199.shoption.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader
import java.io.IOException

class AddProductActivity : BaseActivity(),View.OnClickListener {

    private var mSelectedImageFileURI: Uri?=null
    private var mProductImageURL:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        setupActionBar()
        findViewById<ImageView>(R.id.iv_add_update_product).setOnClickListener(this)
        findViewById<Button>(R.id.btn_submit_add_product).setOnClickListener(this)

    }
    private fun setupActionBar(){
        setSupportActionBar(findViewById(R.id.toolbar_add_product_activity))
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_add_product_activity).setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
            if (v != null) {
                when (v.id) {

                    // The permission code is similar to the user profile image selection.
                    R.id.iv_add_update_product -> {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            Constants.showImageChooser(this@AddProductActivity)
                        } else {
                            /*Requests permissions to be granted to this application. These permissions
                             must be requested in your manifest, they should not be granted to your app,
                             and they should have protection level*/
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                Constants.READ_STORAGE_PERMISSION_CODE
                            )
                        }
                    }
                    R.id.btn_submit_add_product ->{
                        if(validateProductDetails()){
                            uploadProductImage()
                        }
                    }

                }
            }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //showErrorSnackBar("The storage permission is granted.",false)
                Constants.showImageChooser(this)
            }
            else{
                Toast.makeText(this,resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    findViewById<ImageView>(R.id.iv_add_update_product).setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_vector_edit))

                    mSelectedImageFileURI =data.data!!

                    try {
                        GlideLoader(this).loadUserPicture(mSelectedImageFileURI!!,findViewById(R.id.iv_product_image))
                    }catch (e:IOException){
                        e.printStackTrace()
                    }
                }
            }
        } else if (resultCode== Activity.RESULT_CANCELED){
            Log.e("Request Cancelled","Image selection cancelled")
        }
    }

    private fun validateProductDetails(): Boolean {
        return when {

            mSelectedImageFileURI == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_product_title).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_title), true)
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_product_price).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_price), true)
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_product_description).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_description),
                    true
                )
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_product_quantity).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_quantity),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

//    /**
//     * A function to upload the selected product image to firebase cloud storage.
//     */
   private fun uploadProductImage() {
//
       showProgressDialog(resources.getString(R.string.please_wait))
//
       FireStoreClass().uploadImageToCloudStorage(
            this@AddProductActivity,
            mSelectedImageFileURI,
            Constants.PRODUCT_IMAGE
        )
    }
//
//    /**
//     * A function to get the successful result of product image upload.
//     */
    fun imageUploadSuccess(imageURL: String) {
          //hideProgressDialog()
    //showErrorSnackBar("Product image is uploaded successfully.Image URL:$imageURL",false)

        // Initialize the global image url variable.
        mProductImageURL = imageURL

       uploadProductDetails()
    }
//
   private fun uploadProductDetails() {
//
//        // Get the logged in username from the SharedPreferences that we have stored at a time of login.
      val username =
            this.getSharedPreferences(Constants.SHOPTION_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!

//        // Here we get the text from editText and trim the space
        val product = Product(
            FireStoreClass().getCurrentUserID(),
            username,
            findViewById<EditText>(R.id.et_product_title).text.toString().trim { it <= ' ' },
            findViewById<EditText>(R.id.et_product_price).text.toString().trim { it <= ' ' },
            findViewById<EditText>(R.id.et_product_description).text.toString().trim { it <= ' ' },
            findViewById<EditText>(R.id.et_product_quantity).text.toString().trim { it <= ' ' },
            mProductImageURL
        )

        FireStoreClass().uploadProductDetails(this@AddProductActivity, product)
   }
//
//    /**
//     * A function to return the successful result of Product upload.
//     */
    fun productUploadSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            this@AddProductActivity,
            resources.getString(R.string.product_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }



}