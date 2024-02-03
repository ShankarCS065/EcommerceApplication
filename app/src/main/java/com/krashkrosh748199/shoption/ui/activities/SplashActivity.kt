package com.krashkrosh748199.shoption.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler().postDelayed(
            {
                val currentUserID = FireStoreClass().getCurrentUserID()
               if(currentUserID.isNotEmpty()) {
                    //Launch the main activity
                    startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
               }else{
                   startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                }
                finish()
            },
            1500

        )
//        val tv_app_name=findViewById<TextView>(R.id.tv_app_name)
//        val typeface:Typeface=Typeface.createFromAsset(assets,"MontserratAlternates-Bold.otf")
//        tv_app_name.typeface=typeface
    }
}