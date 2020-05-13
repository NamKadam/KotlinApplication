package com.essensys.kotlinapplication.View.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.utils.App


class SplashScreenActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    protected var splash_time = 2000
    var splashFlag = false
    private var mThread: Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        moveToNxtActivity()
    }

    //method to move next activity
    private fun moveToNxtActivity() {
//        mThread = object : Thread() {
//            override fun run() {
//                try {
//                    synchronized(this) {
//                        (splash_time.toLong()) }
//                } catch (e: InterruptedException) {
//                } finally {
//                    if (!splashFlag) {
//                        splashFlag = true
//                        val it: Intent
//                        if (!App.isUserLoggedIn(this@SplashScreenActivity)) {
//                            it = Intent(this@SplashScreenActivity, LoginActivity::class.java)
//                            startActivity(it)
//                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
//                            finish()
//                        } else {
//                            it = Intent(this@SplashScreenActivity, HomeActivity::class.java)
//                            startActivity(it)
//                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
//                            finish()
//                        }
//                    }
//                }
//            }
//        }
//        mThread!!.start()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val it: Intent
            if (!App.isUserLoggedIn(this@SplashScreenActivity)) {
                            it = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                            startActivity(it)
                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
                            finish()
                        } else {
                            it = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                            startActivity(it)
                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
                            finish()
                        }

            // close this activity
            finish()
        }, splash_time.toLong())
    }
}