package com.essensys.kotlinapplication.View.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.utils.SharedPreferenceManager

open class BaseActivity:AppCompatActivity() {
    var actionBarTitle: AppCompatTextView?=null
    var actionBarProfName: AppCompatTextView?=null
    var actionBarBack: AppCompatImageView? = null
    var actionLogout:AppCompatImageView? = null
    var actionSave:AppCompatImageView? = null
    var actionProf:AppCompatImageView? = null
    var mRelprof: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        //        // Inflate your custom layout
        val actionBarLayout = layoutInflater.inflate(
            R.layout.layout_custom_action_bar,
            null
        ) as ViewGroup

        // Set up your ActionBar

        // Set up your ActionBar
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowHomeEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.customView = actionBarLayout

        actionBarTitle = findViewById(R.id.tvHeaderTitle)
        actionBarBack = findViewById(R.id.btnLeftBack)
        actionSave = findViewById(R.id.btnSave)
        actionLogout = findViewById(R.id.btnLogout)
        actionBarProfName = findViewById(R.id.tvProfName)
        actionProf = findViewById(R.id.btnprof)
        mRelprof = findViewById(R.id.rel_prof)
        actionLogout!!.setOnClickListener(View.OnClickListener {
            SharedPreferenceManager.with(this@BaseActivity).logout(this@BaseActivity)
            //                Log.d(TAG, "Now log out and start the activity login");
            val intent = Intent(
                this@BaseActivity,
                LoginActivity::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })
        actionBarBack!!.setOnClickListener(View.OnClickListener { onBackPressed() })

    }
}