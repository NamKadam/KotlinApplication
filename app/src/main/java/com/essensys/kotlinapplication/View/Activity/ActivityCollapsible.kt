package com.essensys.kotlinapplication.View.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.essensys.kotlinapplication.Controller.GetWorkDataController
import com.essensys.kotlinapplication.Model.ActionLogsData
import com.essensys.kotlinapplication.Model.AssignToUserData
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.View.Adapter.SimpleFragmentPagerAdapter
import com.essensys.kotlinapplication.utils.CommonUtilities
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.util.*

class ActivityCollapsible:BaseActivity(), GetWorkDataController.WorkDataListener {
    private val mBmp: Bitmap? = null
    private val destination: File? = null
    private var stringArrayListImage: ArrayList<File>? = null
    private var viewPager: ViewPager? = null
    private var adapter: SimpleFragmentPagerAdapter? = null
    private var tabLayout: TabLayout? = null
    private var dataWorkList: DataWorkList? = null
    private var actionLogsDataList: ArrayList<ActionLogsData>? = null
    private var mtxtname: TextView? = null
    private  var mTxtMob:TextView? = null
    private  var mTxtEmail:TextView? = null
    private  var mTxtAdd:TextView? = null
    private  var mTxtCity:TextView? = null
    private  var mTxtComName:TextView? = null
    private  var mTxtDateWork:TextView? = null
    private var mRelEmail: RelativeLayout? = null
    private  var mRelMob:RelativeLayout? = null
    private var mAppBar: AppBarLayout? = null
    var mGetWorkDetController:GetWorkDataController?=null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsible)
        initUI()
    }
//initialization of UI
    private fun initUI() {
    val mTxt = findViewById(R.id.txt) as TextView
    stringArrayListImage = ArrayList()
    actionBarBack!!.visibility = View.VISIBLE
    actionBarTitle!!.text = "Work Details"

    mAppBar = findViewById(R.id.appbar_workdet)
    mAppBar!!.setVisibility(View.GONE)

    mtxtname = findViewById(R.id.txtName_work1)
    mTxtMob = findViewById(R.id.txtMob_work1)
    mTxtEmail = findViewById(R.id.txtEmail_work1)
    mTxtAdd = findViewById(R.id.txtaddress_work1)
    mTxtCity = findViewById(R.id.txt_city)
    mTxtComName = findViewById(R.id.txt_comp_name)
    mTxtDateWork = findViewById(R.id.txtdate_work)
    mRelEmail = findViewById(R.id.rel_mail)
    mRelMob = findViewById(R.id.rel_mobile)
    progressDialog=CommonUtilities.getDefaultLoader(this)
    mGetWorkDetController= GetWorkDataController(this,this)
    mRelMob!!.setOnClickListener(View.OnClickListener {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + mTxtMob!!.getText().toString())
        startActivity(dialIntent)
    })
    val toolbar: Toolbar = findViewById(R.id.toolbar)

    }

    override fun onResume() {
        super.onResume()
        //calling webservice
        showLoader()
        mGetWorkDetController!!.getworkData(intent.getStringExtra("work_id"))
    }

    private fun showLoader() {
        if (progressDialog == null || progressDialog!!.isShowing) {
            return
        }
        Handler(Looper.getMainLooper()).post { progressDialog!!.show() }
    }

    private fun hideLoader() {
        if (progressDialog == null || !progressDialog!!.isShowing) {
            return
        }
        Handler(Looper.getMainLooper()).post { progressDialog!!.dismiss() }
    }
    private fun setViewpager(
        flagAssigneeVisibility: String,
        flagStatusVisible: String
    ) {
        viewPager = findViewById(R.id.viewpager) as ViewPager

        // Create an adapter that knows which fragment should be shown on each page
        adapter = SimpleFragmentPagerAdapter(
            this,
            supportFragmentManager,
            intent.getStringExtra("work_id"),
            flagAssigneeVisibility,
            flagStatusVisible
        )
        // Set the adapter onto the view pager
        viewPager!!.setAdapter(adapter)

        // Give the TabLayout the ViewPager
        tabLayout = findViewById(R.id.sliding_tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
    }

    override fun onListFetchSuccess(
        dataWorkLists: DataWorkList?,
        actionLogsData: ArrayList<ActionLogsData>?,
        assignToUserDataLists: ArrayList<AssignToUserData>?,
        flagAssigneeVisibility: String?,
        flagStatusVisibility: String?
    ) {
        hideLoader()
        dataWorkList = dataWorkLists
        actionLogsDataList = actionLogsDataList

        getData(dataWorkLists!!, assignToUserDataLists!!)
        mAppBar!!.visibility = View.VISIBLE
        setViewpager(flagAssigneeVisibility!!, flagStatusVisibility!!)
    }

    //method
    private fun getData(
        dataWorkLists: DataWorkList,
        assignToUserDataArrayList: ArrayList<AssignToUserData>
    ) {
//
        mtxtname!!.setText(dataWorkLists.name)
        mTxtMob!!.setText(dataWorkLists.mobile)
        if (dataWorkLists.email == null || dataWorkLists.email.equals("")) {
            mRelEmail!!.visibility = View.GONE
        } else {
            mRelEmail!!.visibility = View.VISIBLE
            mTxtEmail!!.setText(dataWorkLists.email)
        }
        mTxtAdd!!.setText(
            dataWorkLists.customerAddress.toString() + "," + dataWorkLists.routeName)
        mTxtCity!!.setText(dataWorkLists.cityName)
        mTxtDateWork!!.setText(dataWorkLists.callScheduleOndate)
    }


    override fun onListFetchFailure(errorMsg: String?) {
        CommonUtilities.showToastOnMainThread(this,errorMsg)
    }
}