package com.krashkrosh748199.shoption.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.User
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader

class SettingsActivity : BaseActivity(), View.OnClickListener {
    private  lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupActionBar()
        findViewById<TextView>(R.id.tv_edit).setOnClickListener(this@SettingsActivity)
        findViewById<Button>(R.id.btn_logout).setOnClickListener(this@SettingsActivity)
        findViewById<LinearLayout>(R.id.ll_address).setOnClickListener(this@SettingsActivity)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_edit -> {
                    val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
                    startActivity(intent)
                }
                R.id.ll_address -> {
                    val intent=Intent(this@SettingsActivity,AddressListActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_logout -> {

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            }
        }
    }

     private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_settings_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_settings_activity).setNavigationOnClickListener { onBackPressed() }
    }
//
//     private fun getUserDetails() {
//
//
//        showProgressDialog(resources.getString(R.string.please_wait))
//
//         FireStoreClass().getUserDetails(this@SettingsActivity)
//    }

    private fun getUserDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        // Assuming FireStoreClass is the correct class to interact with Firestore
        FireStoreClass().getUserDetails(this@SettingsActivity)
    }


//    fun userDetailsSuccess(user: User) {
//        mUserDetails=user
//
//        hideProgressDialog()
//
//        // Load the image using the Glide Loader class.
//        user.image?.let { GlideLoader(this@SettingsActivity).loadUserPicture(user.image, findViewById(R.id.iv_user_photo)) }
//
//        findViewById<TextView>(R.id.tv_name).text = "${user.firstName} ${user.lastName}"
//        findViewById<TextView>(R.id.tv_gender).text = user.gender
//        findViewById<TextView>(R.id.tv_email).text = user.email
//        findViewById<TextView>(R.id.tv_mobile_number).text = "${user.mobile}"
//        // END
//    }
fun userDetailsSuccess(user: User) {
    mUserDetails = user

    hideProgressDialog()

    // Load the user image using Glide if the image URL is not null or empty
    user.image?.takeIf { it.isNotBlank() }?.let { imageUrl ->
        val ivUserPhoto = findViewById<ImageView>(R.id.iv_user_photo)
        GlideLoader(this@SettingsActivity).loadUserPicture(imageUrl, ivUserPhoto)
    }

    // Update TextViews with user details
    findViewById<TextView>(R.id.tv_name).text = "${user.firstName} ${user.lastName}"
    findViewById<TextView>(R.id.tv_gender).text = user.gender ?: ""
    findViewById<TextView>(R.id.tv_email).text = user.email ?: ""
    findViewById<TextView>(R.id.tv_mobile_number).text = "${user.mobile}"
}


    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

}