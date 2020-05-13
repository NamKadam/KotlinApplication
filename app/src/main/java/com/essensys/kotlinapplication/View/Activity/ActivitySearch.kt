package com.essensys.kotlinapplication.View.Activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.essensys.kotlinapplication.Controller.GetFieldControler
import com.essensys.kotlinapplication.Model.CategoryData
import com.essensys.kotlinapplication.Model.CityData
import com.essensys.kotlinapplication.Model.ProductData
import com.essensys.kotlinapplication.Model.RouteData
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.utils.CommonUtilities
import java.util.*

class ActivitySearch: BaseActivity(), View.OnClickListener, GetFieldControler.GetFieldListener {
    var mLinearFrmdate: LinearLayout? = null
    var mLinearToDate:LinearLayout? = null
    var mSpnrCity: Spinner? = null
    var mSpnrRoute:Spinner? = null
    var mSpnrCategory:Spinner? = null
    var mSpnrprod:Spinner? = null
     var datePickerDialog: DatePickerDialog? = null
     var year = 0
    var month:Int = 0
    var dayOfMonth:Int = 0
     var frmdate = ""
    var todate= ""
    var city_id = ""
    var cat_id= ""
    var route_id= ""
    var prod_id = ""
     var yearTo = 0
    var monthTo:Int = 0
    var dayOfMonthTo:Int = 0
     var mArraylistCity: ArrayList<CityData>? = null
     var mArraylistRoute: ArrayList<RouteData>? = null
     var mArraylistCat: ArrayList<CategoryData>? = null
     var mArraylistProd: ArrayList<ProductData>? = null
     var mBtnSearch: Button? = null
     var calendar: Calendar? = null
     var isSelected = false
     var mTxtFrmDate: TextView? = null
     var mTxtToDate:TextView? = null
     var flagSpnr = ""
     var mGetFieldcontroller:GetFieldControler?=null
     var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initUI()
    }

    private fun initUI() {
        actionBarBack!!.visibility = View.VISIBLE
        actionBarBack!!.setOnClickListener { onBackPressed() }
        actionBarTitle!!.text = "Search"
        actionLogout!!.visibility = View.GONE
        mRelprof!!.visibility = View.GONE
        mArraylistCity = ArrayList()
        mArraylistCat = ArrayList()
        mArraylistProd = ArrayList()
        mArraylistRoute = ArrayList()

        mLinearFrmdate = findViewById(R.id.linear_frmdate)
        mLinearToDate = findViewById(R.id.linear_todate)
        mTxtFrmDate = findViewById(R.id.txtFrmDate)
        mTxtToDate = findViewById(R.id.txtToDate)
        mSpnrCity = findViewById(R.id.spnr_city_workList)
        mSpnrRoute = findViewById(R.id.spnr_route_workList)
        mSpnrCategory = findViewById(R.id.spnr_cate_workList)
        mSpnrprod = findViewById(R.id.spnr_prod_workList)
        mBtnSearch = findViewById(R.id.btn_srch_work)

        mLinearFrmdate!!.setOnClickListener(this)
        mLinearToDate!!.setOnClickListener(this)
        mBtnSearch!!.setOnClickListener(this)
        progressDialog=CommonUtilities.getDefaultLoader(this)
        mGetFieldcontroller= GetFieldControler(this,this)
    }

    override fun onResume() {
        super.onResume()
        flagSpnr = "0"
        showLoader()
        mGetFieldcontroller!!.fetchGetFieldsManager(city_id, cat_id)
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
         R.id.linear_frmdate->
             datePickerFrom()
          R.id.linear_todate->
                datePickerTo()
         R.id.btn_srch_work->
         {
             var  intent: Intent = Intent()
             intent.putExtra("frmdate", frmdate)
             intent . putExtra( "todate", todate)
             intent.putExtra("city_id", city_id)
             intent.putExtra("route_id", route_id)
             intent . putExtra ("cat_id", cat_id)
             intent.putExtra("prod_id", prod_id)
             setResult(1, intent)
             finish ()
         }
     }
    }

    //method for datepickerFrom dialog
    private fun datePickerFrom() {
        calendar = Calendar.getInstance()
        year = calendar!!.get(Calendar.YEAR)
        month = calendar!!.get(Calendar.MONTH)
        dayOfMonth = calendar!!.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(
            this@ActivitySearch,
            OnDateSetListener { datePicker, year, month, day ->
                frmdate = day.toString() + "/" + (month + 1) + "/" + year
                mTxtFrmDate!!.text = day.toString() + "/" + (month + 1) + "/" + year
            }, year, month, dayOfMonthTo
        )
        //        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog!!.show()
    }

    //method for datepickerTo dialog
    private fun datePickerTo() {
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        try {
//            date = format.parse(dtStart);
//            System.out.println("Date ->" + date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar mCalendar = null;
//        if(calendar!=null)
//        {
//            mCalendar = Calendar.getInstance();
//            mCalendar.setTimeInMillis(calendar.getTimeInMillis());
//
//        }
//        mCalendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
        val mCalendar: Calendar
        if (!frmdate.equals("", ignoreCase = true)) {
            val strDate = frmdate.split("/").toTypedArray()
            mCalendar = Calendar.getInstance()
            //imp
            mCalendar[strDate[2].toInt(), strDate[1].toInt() - 1] = strDate[0].toInt() + 1
        } else {
            mCalendar = Calendar.getInstance()
        }
        yearTo = mCalendar[Calendar.YEAR]
        monthTo = mCalendar[Calendar.MONTH]
        dayOfMonthTo = mCalendar[Calendar.DAY_OF_MONTH]
        datePickerDialog = DatePickerDialog(
            this@ActivitySearch,
            OnDateSetListener { datePicker, year, month, day ->
                todate = day.toString() + "/" + (month + 1) + "/" + year
                mTxtToDate!!.text = day.toString() + "/" + (month + 1) + "/" + year
            }, yearTo, monthTo, dayOfMonthTo
        )
        //        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
        datePickerDialog!!.getDatePicker().minDate = mCalendar.timeInMillis - 1000
        datePickerDialog!!.show()
    }

    //response
    override fun onListFetchSuccess(mArraylistCityTemp: ArrayList<CityData>?, mArraylistRouteTemp: ArrayList<RouteData>?,
        mArraylistProduct: ArrayList<ProductData>?,
        mArraylistCatTemp: ArrayList<CategoryData>?
    ) {
        //for city
        if (!mArraylistCity!!.isEmpty() || mArraylistCity != null) {
            mArraylistCity!!.clear()
        }
        var cityData:CityData= CityData()
        cityData.setCityName("Select City")
        mArraylistCity!!.add(cityData)

        if(mArraylistCityTemp!!.size>0)
        {
           mArraylistCity!!.addAll(mArraylistCityTemp)
        }

        //for route
        if(mArraylistRoute!!.isEmpty()&&mArraylistRoute!=null)
        {
            mArraylistRoute!!.clear()
        }
        var routeData:RouteData= RouteData()
        routeData.setRouteName("Select Route")
        mArraylistRoute!!.add(routeData)

        if(mArraylistRouteTemp!!.size>0)
        {
            mArraylistRoute!!.addAll(mArraylistRouteTemp)
        }

        //for product
        if (!mArraylistProd!!.isEmpty() || mArraylistProd != null) {
            mArraylistProd!!.clear()
        }

        val productData = ProductData()
        productData.setProductName("Select Product")
        mArraylistProd!!.add(productData)
        if (mArraylistProduct!!.size > 0) {
            mArraylistProd!!.addAll(mArraylistProduct)
        }

        //for category
        //for category
        if (!mArraylistCat!!.isEmpty() || mArraylistCat != null) {
            mArraylistCat!!.clear()
        }
        val categoryData = CategoryData()
        categoryData.setCatName("Select Category")
        mArraylistCat!!.add(categoryData)

        if (mArraylistCatTemp!!.size > 0) {
            mArraylistCat!!.addAll(mArraylistCatTemp)
        }

        if (flagSpnr.equals("1", ignoreCase = true)) {
            setSpinnerCityRoute(mArraylistCity, mArraylistRoute!!)
        } else if (flagSpnr.equals("2", ignoreCase = true)) {
            setSpinnerCatProd(mArraylistCat, mArraylistProd)
        } else {
            setSpinnerCityRoute(mArraylistCity, mArraylistRoute)
            setSpinnerCatProd(mArraylistCat, mArraylistProd)
        }

    }

    //method to set spinner adapter
    private fun setSpinnerCityRoute(
        mArraylistCity: ArrayList<CityData>?,
        mArraylistRoute: ArrayList<RouteData>?
    ) {
        hideLoader()
        if (flagSpnr.equals("0", ignoreCase = true)) {
            var aa: ArrayAdapter<*> = ArrayAdapter<Any?>(
                this, android.R.layout.simple_spinner_item, mArraylistCity as List<Any?>?
            )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Setting the ArrayAdapter data on the Spinner
            mSpnrCity!!.adapter = aa
            mSpnrCity!!.setSelection(0, true)
            mSpnrCity!!.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        if (isSelected) {
                            city_id = mArraylistCity!![position].getCityId()!!
                            flagSpnr = "1" //for route
                            showLoader()
                            mGetFieldcontroller!!.fetchGetFieldsManager(city_id, cat_id)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        //to set arrayadapter for route
        val rt: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this@ActivitySearch,
            android.R.layout.simple_spinner_item,
            mArraylistRoute as List<Any?>?
        )
        rt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        mSpnrRoute!!.adapter = rt
        mSpnrRoute!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    if (isSelected) {
                        route_id = mArraylistRoute!![position].getRouteId()!!
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        mSpnrRoute!!.setOnTouchListener { v, event ->
            isSelected = true
            false
        }
        mSpnrCity!!.setOnTouchListener { v, event ->
            isSelected = true
            false
        }
    }

    //method to set spinner adapter
    private fun setSpinnerCatProd(
        mArraylistCat: ArrayList<CategoryData>?,
        mArraylistProd: ArrayList<ProductData>?
    ) {
        hideLoader()
        if (flagSpnr.equals("0", ignoreCase = true)) {
            val aa: ArrayAdapter<*> =
                ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item,
                    mArraylistCat as List<Any?>?
                )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Setting the ArrayAdapter data on the Spinner
            mSpnrCategory!!.adapter = aa
            mSpnrCategory!!.setSelection(0, true)
            mSpnrCategory!!.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        if (isSelected) {
                            cat_id = mArraylistCat!![position].getCatId()!!
                            flagSpnr = "2"
                            showLoader()
                            mGetFieldcontroller!!.fetchGetFieldsManager(city_id, cat_id)

                            //                            getFields(user_id, company_id, user_type, city_id, cat_id);
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        //for product
        val prod: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this@ActivitySearch,
            android.R.layout.simple_spinner_item,
            mArraylistProd as List<Any?>?
        )
        prod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        mSpnrprod!!.adapter = prod
        mSpnrprod!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    if (isSelected) {
                        prod_id = mArraylistProd!![position].getProductId()!!
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        mSpnrprod!!.setOnTouchListener { v, event ->
            isSelected = true
            false
        }
        mSpnrCategory!!.setOnTouchListener { v, event ->
            isSelected = true
            false
        }
//
    }
    override fun onListFetchFailure(errorMsg: String?) {
        TODO("Not yet implemented")
    }
}