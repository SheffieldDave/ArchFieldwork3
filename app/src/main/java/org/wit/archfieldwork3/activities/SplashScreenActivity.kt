package org.wit.archfieldwork3.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.views.login.LoginView
import org.wit.archfieldwork3.views.sitelist.SiteListView

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val background = object : Thread() {
            override fun run() {
                try{
                    Thread.sleep(5000)

                    val intent = Intent(baseContext, LoginView::class.java)
                    startActivity(intent)


                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
        background.start()
    }
}
