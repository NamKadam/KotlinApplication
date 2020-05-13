package com.essensys.kotlinapplication.View.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.essensys.kotlinapplication.BuildConfig
import com.essensys.kotlinapplication.Controller.HomeController
import com.essensys.kotlinapplication.Model.HomeData
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.ViewModel.HomeViewModel
import com.essensys.kotlinapplication.utils.App
import com.essensys.kotlinapplication.utils.CommonUtilities

class HomeActivity : BaseActivity(), View.OnClickListener, HomeController.HomeDataListener {
    var mCardTodays:CardView?=null
    var mCardClosed:CardView?=null
    var mCardReschedule:CardView? = null
    var mTxtCntTodays:TextView?=null
    var mTxtCntRe:TextView? = null
    var mTxtCntClosed:TextView? = null
    var mTxtCompName:TextView?=null

    private lateinit var mImgCompLogo: ImageView
    private var progressDialog: ProgressDialog? = null
    private var Version_name = 0
    private var homeViewModel:HomeViewModel?=null
    private var mHomeController:HomeController?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home);
        init();
    }

    private fun init() {
        mCardTodays=findViewById(R.id.card_todays)
        mCardClosed=findViewById(R.id.card_closed)
        mCardReschedule=findViewById(R.id.card_resched)
        mTxtCntTodays=findViewById(R.id.txt_count)
        mTxtCntRe=findViewById(R.id.txt_count_resched)
        mTxtCntClosed=findViewById(R.id.txt_count_closed)

        mRelprof!!.visibility = View.VISIBLE
        actionBarProfName!!.setText(
            App.getCurrentUser(this)!!.getUserName().toString() + " (" + App.getCurrentUser(this)
            !!.getUserType() + ")"
        )
        mCardTodays = findViewById(R.id.card_todays)
        mCardReschedule = findViewById(R.id.card_resched)
        mCardClosed = findViewById(R.id.card_closed)
        mTxtCntTodays = findViewById(R.id.txt_count)
        mTxtCntRe = findViewById(R.id.txt_count_resched)
        mTxtCntClosed = findViewById(R.id.txt_count_closed)

        mTxtCompName = findViewById(R.id.txt_comp_name)
        mImgCompLogo = findViewById(R.id.img_comp_logo)
        actionBarBack!!.visibility = View.GONE
        actionLogout!!.visibility = View.VISIBLE

        mCardTodays!!.setOnClickListener(this)
        mCardReschedule!!.setOnClickListener(this)
        mCardClosed!!.setOnClickListener(this)
        this.progressDialog = CommonUtilities.getDefaultLoader(this)
        Version_name = BuildConfig.VERSION_CODE
        mHomeController= HomeController(this,this)
//        homeViewModel=ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private fun showLoader()
    {
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

    override fun onResume() {
        super.onResume()
        var userId: String? =App.getCurrentUser(this)!!.getUserId()
        var userType: String? =App.getCurrentUser(this)!!.getUserType()
        var companyId: String? =App.getCurrentUser(this)!!.getCompanyId()
//        getHomeDataVVM()
        showLoader()
        mHomeController!!.getHomeData(userId,userType,companyId)
    }

     //webservice for home data
    private fun getHomeDataVVM() {
         var userId: String? =App.getCurrentUser(this)!!.getUserId()
         var userType: String? =App.getCurrentUser(this)!!.getUserType()
         var companyId: String? =App.getCurrentUser(this)!!.getCompanyId()

         homeViewModel!!.getHomeResponsiveData(userId,userType,companyId).observe(this, Observer { response->
             hideLoader()
             if(response!=null)
            {
             var strResp:HomeData=response

                Glide.with(this)
                    .load(strResp.getCompanyLogo())
                    .placeholder(R.drawable.logo)
                    .into(mImgCompLogo)
                mTxtCompName!!.text=strResp.getCompanyName()
                mTxtCntTodays!!.text=(strResp.getTodayWorkCount())
                mTxtCntClosed!!.setText(strResp.getClosedWorkCount())
                mTxtCntRe!!.setText(strResp.getRescheduleWorkCount())
//                appUrl = result.getString("appUrl")
            }
     })
    }

    override fun onClick(v: View?) {
        var intent: Intent?=null
        intent = Intent(this@HomeActivity, WorkListActivity::class.java)
        when(v!!.id)
        {
            R.id.card_todays -> {
                intent.putExtra("work_status","")
                startActivity(intent)
            }

            R.id.card_closed ->
            {
                intent.putExtra("work_status","1")
                startActivity(intent)
            }
            R.id.card_resched ->{
                intent.putExtra("work_status","2")
                startActivity(intent)
            }
        }
    }

    override fun onListFetchSuccess(
        mContext: Context?, msg: String?,
        msg_string:String?, appUrl: String?, appVersionCode: String?,
        homeData: HomeData?
    )
    {
        if(msg.equals("1"))
        {
            Glide.with(this)
                .load(homeData!!.getCompanyLogo())
                .placeholder(R.drawable.logo)
                .into(mImgCompLogo)
            mTxtCompName!!.text=homeData.getCompanyName()
            mTxtCntTodays!!.text=homeData.getTodayWorkCount()
            mTxtCntClosed!!.text=homeData.getClosedWorkCount()
            mTxtCntRe!!.text=homeData.getRescheduleWorkCount()
            //to move to next activity
            if (appVersionCode!!.toInt() > Version_name) {

                dialogUpdate(appUrl)
            }
        }else
        {
            Toast.makeText(this,msg_string,Toast.LENGTH_LONG).show()
        }
        hideLoader()

    }

    override fun onListFetchFailure(errorMsg: String?) {
      CommonUtilities.showToastOnMainThread(this,errorMsg)
    }

    //custom dialog for update app
    private fun dialogUpdate(appUrl: String?) {
        val dialog = Dialog(this, R.style.ThemeTransparentDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.layout_update_dialog)
        //        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);
//
//        dialog.getWindow().setLayout(width, height);
        dialog.setCancelable(true)
        //        dialog.setCanceledOnTouchOutside(true);
        // set the custom dialog components - text, image and button
        val dialogButton =
            dialog.findViewById<Button>(R.id.btnupdate)
        dialogButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(appUrl)
                )
            )
        }
        dialog.show()
    }
}
