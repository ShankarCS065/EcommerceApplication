package com.krashkrosh748199.shoption.ui.activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.User

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()

        findViewById<TextView>(R.id.tv_login).setOnClickListener {
            onBackPressed()
        }
        findViewById<Button>(R.id.btn_register).setOnClickListener{

            registerUser()

        }

    }

    private fun setupActionBar(){

        val toolbarRegisterActivity=findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_register_activity)
        setSupportActionBar(toolbarRegisterActivity)

        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbarRegisterActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_first_name).text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
                false
            }
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_last_name).text.toString().trim { it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
                false
            }
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_email).text.toString().trim { it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_password).text.toString().trim { it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(findViewById<EditText>(R.id.et_confirm_password).text.toString().trim { it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
                false
            }
           findViewById<EditText>(R.id.et_password).text.toString().trim {it <= ' '}!= findViewById<EditText>(R.id.
               et_confirm_password).text.toString()
               .trim {it <= ' '} -> {
                   showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
                   false
               }
           !findViewById<AppCompatCheckBox>(R.id.cb_terms_and_condition).isChecked-> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition),true)
                false
            }
            else -> {
              //  showErrorSnackBar(resources.getString(R.string.register_successfully),false)
                true
            }

        }

    }

    private fun registerUser(){
        if(validateRegisterDetails()){

            showProgressDialog(resources.getString(R.string.please_wait))

            val email:String= findViewById<EditText>(R.id.et_email).text.toString().trim{ it<= ' '}
            val password:String=findViewById<EditText>(R.id.et_password).text.toString().trim{ it <= ' '}
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        if(task.isSuccessful){
                            val firebaseUser: FirebaseUser =task.result!!.user!!

                            val user= User(
                                firebaseUser.uid,
                                findViewById<EditText>(R.id.et_first_name).text.toString().trim { it<=' ' },
                                findViewById<EditText>(R.id.et_last_name).text.toString().trim { it<=' ' },
                                email

                            )
                            FireStoreClass().registerUser(this@RegisterActivity,user)


//                            FirebaseAuth.getInstance().signOut()
//                            finish()

                        }
                        else{
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    })
        }
    }

    fun userRegistrationSuccess(){
        hideProgressDialog()

        Toast.makeText(this@RegisterActivity,resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
            ).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }



}