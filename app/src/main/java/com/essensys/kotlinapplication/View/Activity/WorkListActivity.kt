package com.essensys.kotlinapplication.View.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.View.Adapter.AdapterCustManagement
import com.essensys.kotlinapplication.ViewModel.WorListViewModel
import com.essensys.kotlinapplication.remote.PaginationScrollListener
import com.essensys.kotlinapplication.utils.App
import com.essensys.kotlinapplication.utils.CommonUtilities
import com.essensys.kotlinapplication.utils.DefaultItemAnimator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class WorkListActivity:BaseActivity(), View.OnClickListener {
    private var mRecycl: RecyclerView? = null
    private var mTxtMsg: TextView? = null
    private var layoutManager: LinearLayoutManager? = null
    private var dataWorkListArrayList: ArrayList<DataWorkList>? = null
    private  var mArraylistFilter:ArrayList<DataWorkList>? = null
    private var mFab: FloatingActionButton? = null
    private var mLinAll: LinearLayout? =null
    private  var mLinearSearch:LinearLayout? = null
    private  var mLinearSrchlay:LinearLayout? = null
    private var progressDialog: ProgressDialog? = null
    private var offset:Int?= null
    private var flagSpnr:String? = ""
    private var work_status:String? = ""
    private var user_id:String? = ""
    private var company_id:String? = ""
    private var user_type:String?= ""
    private var frmdate:String? = ""
    private var todate:String? = ""
    private var city_id:String? = ""
    private var cat_id:String? = ""
    private var route_id:String? = ""
    private  var prod_id:String? = ""

    val PAGE_START = 0 //added by ND
    val isLoading = false //added by ND
    val isLastPage = false //added by ND
    val TOTAL_PAGES = 1 //added by ND
    var currentPage = PAGE_START //added by ND

    private var mImgFilter: ImageView? = null
    private val flagSrch = "0"
    private var mEdtSearch: TextInputEditText? = null
    private var mWorkListModel:WorListViewModel?=null
    private var mAdapterCust: AdapterCustManagement? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_management_list)
         initUI()
    }
   //initilization of UI
    @SuppressLint("RestrictedApi")
    private fun initUI() {
       mRecycl = findViewById(R.id.recycl_cust)
       mTxtMsg = findViewById(R.id.txt_msg)
       mLinearSearch = findViewById(R.id.linear_search)
       mLinAll = findViewById(R.id.lin_all)

       mLinearSrchlay = findViewById(R.id.linear_search_lay)
       mImgFilter = findViewById(R.id.img_srch)
       progressDialog = CommonUtilities.getDefaultLoader(this)
       mEdtSearch = findViewById(R.id.edt_search)

       mLinearSearch!!.setOnClickListener(this)
       mImgFilter!!.setOnClickListener(this)
       mLinAll!!.setOnClickListener(this)

       mFab = findViewById(R.id.fab)

       dataWorkListArrayList = ArrayList()
       mArraylistFilter= ArrayList()
       mAdapterCust=null
       //recyclerview initialisation
       layoutManager = LinearLayoutManager(this)
       mRecycl!!.setLayoutManager(layoutManager)
       mRecycl!!.setItemAnimator(DefaultItemAnimator())
       mFab!!.setVisibility(View.GONE)

       user_id = App.getCurrentUser(this)!!.getUserId()
       company_id = App.getCurrentUser(this)!!.getCompanyId()
       user_type = App.getCurrentUser(this)!!.getUserType()

       mWorkListModel=ViewModelProviders.of(this).get(WorListViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
//        mAdapterCust = null
        mEdtSearch!!.setText("")
        dataWorkListArrayList!!.clear()
        //method for searching
        searchWorkList()

        offset = 0
        work_status = intent.getStringExtra("work_status")
        if (work_status.equals("1", ignoreCase = true)) {
            actionBarTitle!!.text=resources.getString(R.string.closed_work_list)
        } else if (work_status.equals("2", ignoreCase = true)) {
            actionBarTitle!!.text=resources.getString(R.string.reschedule_work_list)
        } else {
            actionBarTitle!!.text=resources.getString(R.string.today_work_list)
        }
        showLoader()
        getWorkData(user_id,company_id,user_type,todate,frmdate,city_id,route_id,cat_id,prod_id,offset.toString(),work_status)
    }
  //method for searching
    private fun searchWorkList() {
      mEdtSearch!!.addTextChangedListener(object : TextWatcher {
          override fun beforeTextChanged(
              charSequence: CharSequence,
              i: Int,
              i1: Int,
              i2: Int
          ) {
          }

          override fun onTextChanged(
              query: CharSequence,
              i: Int,
              i1: Int,
              i2: Int
          ) {
              var query = query
              query = query.toString().toLowerCase()
              mArraylistFilter = ArrayList()
              mTxtMsg!!.visibility = View.GONE
              if (query != "") {
                  for (j in dataWorkListArrayList!!.indices) {
                      val Name: String = dataWorkListArrayList!![j].name!!.toLowerCase()
                      val city: String = dataWorkListArrayList!![j].city!!.toLowerCase()
                      val mobileNo: String =
                          dataWorkListArrayList!![j].mobile!!.toLowerCase()
                      val email: String =
                          dataWorkListArrayList!![j].email!!.toLowerCase()
                      val assigneeName: String =
                          dataWorkListArrayList!![j].assignToName!!.toLowerCase()
                      val assigneeNameAdmin: String =
                          dataWorkListArrayList!![j].assignToNameAdminType!!.toLowerCase()
                      if (Name.contains(query) || city.contains(query) || mobileNo.contains(query) || email.contains(
                              query
                          ) || assigneeName.contains(query) || assigneeNameAdmin.contains(query)
                      ) {
                          mArraylistFilter!!.add(dataWorkListArrayList!![j])
                      }
                  }
                  if (mArraylistFilter!!.size > 0) {
                      mTxtMsg!!.visibility = View.GONE
                  } else {
                      mTxtMsg!!.visibility = View.VISIBLE
                  }
                  mRecycl!!.adapter = AdapterCustManagement(
                      this@WorkListActivity,
                      mArraylistFilter
                  ) //flagDel=0 for delete icon visible
              }

          }

          override fun afterTextChanged(editable: Editable) {}
      })

  }

    override fun onPause() {
        super.onPause()
         offset=0
    }

    override fun onDestroy() {
        super.onDestroy()
        offset=0
    }
    //webservice for getWorkData
    private fun getWorkData(
       userId: String?,
       userType: String?,
       companyId: String?,
       todate: String?,
       frmdate: String?,
       cityId: String?,
       routeId: String?,
       catId: String?,
       prodId: String?,
       offset: String?,
       workStatus: String?
   ) {
        mWorkListModel!!.getResponsiveLiveData(userId,userType,companyId,todate,frmdate,cityId,routeId,catId,prodId,
            offset,workStatus).observe(this, androidx.lifecycle.Observer { response->
           hideLoader()
            if(response!=null)
            {
                var strResp: ArrayList<DataWorkList>? =response
                if (strResp!!.size > 0) {
                    dataWorkListArrayList!!.addAll(strResp!!)
                    mTxtMsg!!.visibility = View.GONE
                }

                if (!dataWorkListArrayList!!.isEmpty()) {
                    mTxtMsg!!.visibility = View.GONE
                } else {
                    mTxtMsg!!.visibility = View.VISIBLE
                }
//
                if (mAdapterCust != null) {
                    mAdapterCust!!.updateProducts(dataWorkListArrayList)

                } else {
                    mAdapterCust = AdapterCustManagement(this, dataWorkListArrayList)
                    mRecycl!!.adapter = mAdapterCust
                }

                mRecycl!!.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                    override fun loadMoreItems() {
                        isLoading = true
                        currentPage += 1
                        totalPageCount++
                        var offsets=offset!!.toInt()+20
                        showLoader()
                        getWorkData(user_id, company_id, user_type, frmdate, todate, city_id,
                            route_id, cat_id, prod_id, offsets.toString(), work_status
                        )
                    }

                    override var totalPageCount: Int
                        get() = TOTAL_PAGES
                        set(value) {}

                    override val isLastPage: Boolean
                        get() = isLastPage

                    override var isLoading: Boolean
                        get() = isLoading
                        set(value) {}


                })

            }
        })
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

    override fun onClick(v: View?) {
     when(v!!.id)
     {
         R.id.img_srch->
         {
             val intent = Intent(this, ActivitySearch::class.java)
             startActivityForResult(intent, 101)
         }
         R.id.lin_all->{
             offset = 0
             dataWorkListArrayList!!.clear()
             frmdate = ""
             todate = ""
             city_id = ""
             cat_id = ""
             route_id = ""
             prod_id = ""
             showLoader()
             getWorkData(
                 user_id,
                 company_id,
                 user_type,
                 frmdate,
                 todate,
                 city_id,
                 route_id,
                 cat_id,
                 prod_id,
                 offset.toString(),
                 work_status
             )
             mLinAll!!.visibility = View.GONE
         }
     }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 1) {
                offset = 0
                frmdate = data!!.getStringExtra("frmdate")
                todate = data.getStringExtra("todate")
                city_id = data.getStringExtra("city_id")
                route_id = data.getStringExtra("route_id")
                cat_id = data.getStringExtra("cat_id")
                prod_id = data.getStringExtra("prod_id")
                if (!frmdate.equals("", ignoreCase = true) || !todate.equals(
                        "",
                        ignoreCase = true
                    ) || !city_id.equals("", ignoreCase = true) || !route_id.equals(
                        "",
                        ignoreCase = true
                    ) || !cat_id.equals("", ignoreCase = true) || !prod_id.equals(
                        "",
                        ignoreCase = true
                    )
                ) {
                    mLinAll!!.visibility = View.VISIBLE
                }
                //                getTodaysWorkList();
            }
        }
    }

}