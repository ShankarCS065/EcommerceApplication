package com.krashkrosh748199.shoption.ui.activities

import android.app.Activity
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
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.User
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader
import java.io.IOException

@Suppress("DEPRECATION")
class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri?=null
    private var mUserProfileImageURL:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            mUserDetails=intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        findViewById<EditText>(R.id.et_first_name).setText(mUserDetails.firstName)
        findViewById<EditText>(R.id.et_last_name).setText(mUserDetails.lastName)
        findViewById<EditText>(R.id.et_email).isEnabled=false
        findViewById<EditText>(R.id.et_email).setText(mUserDetails.email)

        if(mUserDetails.profileCompleted==0){
            findViewById<TextView>(R.id.tv_title).text=resources.getString(R.string.title_complete_profile)

            findViewById<EditText>(R.id.et_first_name).isEnabled=false

            findViewById<EditText>(R.id.et_last_name).isEnabled=false

        }
        else{
            setupActionBar()
            findViewById<TextView>(R.id.tv_title).text=resources.getString(R.string.title_edit_profile)
            mUserDetails.image?.let { GlideLoader(this@UserProfileActivity).loadUserPicture(it,findViewById(R.id.iv_user_photo)) }

            if(mUserDetails.mobile!=0L){
                findViewById<EditText>(R.id.et_mobile_number).setText(mUserDetails.mobile.toString())
            }
            if(mUserDetails.gender==Constants.MALE){
                findViewById<RadioButton>(R.id.rb_male).isChecked=true
            }else{
                findViewById<RadioButton>(R.id.rb_female).isChecked=true
            }
        }
        
        findViewById<ImageView>(R.id.iv_user_photo).setOnClickListener(this@UserProfileActivity)

        findViewById<Button>(R.id.btn_submit).setOnClickListener(this@UserProfileActivity)

    }
    private fun setupActionBar(){
        setSupportActionBar(findViewById(R.id.toolbar_user_profile_activity))
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_user_profile_activity).setNavigationOnClickListener { onBackPressed() }
    }


    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.iv_user_photo->{
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
                        ){
                        //showErrorSnackBar("you already have the storage permission.",false)
                        Constants.showImageChooser(this)
                    }

                    else{
                        ActivityCompat.requestPermissions(
                                    this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
                R.id.btn_submit ->{

                   if(validateUserProfileDetails()){

                       showProgressDialog(resources.getString(R.string.please_wait))

                       if (mSelectedImageFileUri!=null)
                       FireStoreClass().uploadImageToCloudStorage(this,mSelectedImageFileUri,Constants.USER_PROFILE_IMAGE)
                       else{
                          updateUserProfileDetails()
                       }

                    }
                }
            }

        }


    }

    private fun updateUserProfileDetails(){
        val userHashMap= HashMap<String,Any>()

        val firstName=findViewById<EditText>(R.id.et_first_name).text.toString().trim { it <= ' ' }
        if(firstName!=mUserDetails.firstName){
            userHashMap[Constants.FIRST_NAME]=firstName
        }
        val lastName=findViewById<EditText>(R.id.et_last_name).text.toString().trim { it <= ' ' }
        if(lastName!=mUserDetails.lastName){
            userHashMap[Constants.LAST_NAME]=lastName
        }
        val mobileNumber=findViewById<EditText>(R.id.et_mobile_number).text.toString().trim { it <= ' ' }

        val gender=if(findViewById<RadioButton>(R.id.rb_male).isChecked){
            Constants.MALE
        }
        else{
            Constants.FEMALE
        }
        if(mUserProfileImageURL.isNotEmpty()){
            userHashMap[Constants.IMAGE]=mUserProfileImageURL
        }

        if(mobileNumber.isNotEmpty() && mobileNumber!=mUserDetails.mobile.toString()){
            userHashMap[Constants.MOBILE]=mobileNumber.toLong()
        }
        if(gender.isNotEmpty() && gender!=mUserDetails.gender){
            userHashMap[Constants.GENDER]=gender
        }
        userHashMap[Constants.GENDER] = gender
        userHashMap[Constants.COMPLETE_PROFILE]=1

        //showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().updateUserProfileData(this,userHashMap)

    }

    fun userProfileUpdateSuccess(){
       hideProgressDialog()
        Toast.makeText(this@UserProfileActivity,
            resources.getString(R.string.msg_profile_update_successfully),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
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
                Toast.makeText(this,resources.getString(R.string.read_storage_permission_denied),Toast.LENGTH_LONG).show()
            }
        }

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                         mSelectedImageFileUri = data.data!!
                        // findViewById<ImageView>(R.id.iv_user_photo).setImageURI(Uri.parse(selectedImageFileUri.toString()))
                        GlideLoader(this).loadUserPicture(
                            mSelectedImageFileUri!!,
                            findViewById(R.id.iv_user_photo)
                        )

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else if (resultCode==Activity.RESULT_CANCELED){
                Log.e("Request Cancelled","Image selection cancelled")
            }
        }

    private fun validateUserProfileDetails():Boolean{
        return when{
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_mobile_number).text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number),true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL:String){
      // hideProgressDialog()

        mUserProfileImageURL = imageURL
        updateUserProfileDetails()

    }


}