package com.krashkrosh748199.shoption.ui.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.utils.Constants


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of Android SharedPreferences
        val sharedPreferences =
            getSharedPreferences(Constants.SHOPTION_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
        // Set the result to the tv_main.
        findViewById<TextView>(R.id.tv_main).text= "The logged in user is $username."
    }
}