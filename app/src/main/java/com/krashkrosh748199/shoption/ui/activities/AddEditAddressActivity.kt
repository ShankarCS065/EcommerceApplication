package com.krashkrosh748199.shoption.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Address
import com.krashkrosh748199.shoption.utils.Constants

class AddEditAddressActivity : BaseActivity() {

    private var mAddressDetails: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_address)
        setupActionBar()

        if(intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)){
            mAddressDetails=intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)
        }

        findViewById<Button>(R.id.btn_submit_address).setOnClickListener{saveAddressToFireStore()}
        findViewById<RadioGroup>(R.id.rg_type).setOnCheckedChangeListener { _, checkedId ->
            if (checkedId==R.id.rb_other){
                findViewById<TextInputLayout>(R.id.til_other_details).visibility=View.VISIBLE
            }else{
                findViewById<TextInputLayout>(R.id.til_other_details).visibility=View.GONE
            }
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_add_edit_address_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_add_edit_address_activity).setNavigationOnClickListener { onBackPressed() }
    }


    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_full_name).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_full_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_phone_number).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_phone_number),
                    true
                )
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_address).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_address), true)
                false
            }

            TextUtils.isEmpty(findViewById<EditText>(R.id.et_zip_code).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }

            findViewById<RadioButton>(R.id.rb_other).isChecked && TextUtils.isEmpty(
                findViewById<EditText>(R.id.et_zip_code).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun saveAddressToFireStore() {

        // Here we get the text from editText and trim the space
        val fullName: String = findViewById<EditText>(R.id.et_full_name).text.toString().trim { it <= ' ' }
        val phoneNumber: String = findViewById<EditText>(R.id.et_phone_number).text.toString().trim { it <= ' ' }
        val address: String = findViewById<EditText>(R.id.et_address).text.toString().trim { it <= ' ' }
        val zipCode: String = findViewById<EditText>(R.id.et_zip_code).text.toString().trim { it <= ' ' }
        val additionalNote: String = findViewById<EditText>(R.id.et_additional_note).text.toString().trim { it <= ' ' }
        val otherDetails: String = findViewById<EditText>(R.id.et_other_details).text.toString().trim { it <= ' ' }

        if (validateData()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            val addressType: String = when {

                findViewById<RadioButton>(R.id.rb_home).isChecked -> {
                    Constants.HOME
                }
                findViewById<RadioButton>(R.id.rb_office).isChecked -> {
                    Constants.OFFICE
                }
                else -> {
                    Constants.OTHER
                }
            }

            val addressModel = Address(
                FireStoreClass().getCurrentUserID(),
                fullName,
                phoneNumber,
                address,
                zipCode,
                additionalNote,
                addressType,
                otherDetails
            )
            FireStoreClass().addAddress(this,addressModel)

        }
    }
    fun addUpdateAddressSuccess() {

        // Hide progress dialog
        hideProgressDialog()

        Toast.makeText(
            this@AddEditAddressActivity,
            resources.getString(R.string.err_your_address_added_successfully),
            Toast.LENGTH_SHORT
        ).show()

        setResult(RESULT_OK)
        finish()
    }
}