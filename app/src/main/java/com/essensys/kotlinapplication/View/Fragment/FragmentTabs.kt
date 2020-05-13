package com.essensys.kotlinapplication.View.Fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essensys.kotlinapplication.Controller.GetWorkDataController
import com.essensys.kotlinapplication.Controller.UpdateAsigneeController
import com.essensys.kotlinapplication.Controller.UpdateWorkController
import com.essensys.kotlinapplication.Model.ActionLogsData
import com.essensys.kotlinapplication.Model.AssignToUserData
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.Model.StorageFile
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.View.Adapter.AdapterActionLogs
import com.essensys.kotlinapplication.View.Adapter.AdapterImages
import com.essensys.kotlinapplication.utils.CommonUtilities
import com.essensys.kotlinapplication.utils.FilePath
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class FragmentTabs: Fragment(), GetWorkDataController.WorkDataListener,
    UpdateWorkController.UpdateWorkListener, UpdateAsigneeController.UpdateAsigneeListener {
    private val RESULT_OK = 100
    private var mCardStatus: CardView? = null
    private var mRelActionLogs: RelativeLayout? = null
    private var bundle: Bundle? = null
    private var mLinearAssign: LinearLayout? = null
    private val PICK_FILE_REQUEST = 0
    private var mRecyclLogs: RecyclerView? = null
    private val mImgExpand: ImageView? = null
    private var mLayoutmanager: LinearLayoutManager? = null
    private val mRelLogs: RelativeLayout? = null
    private var actionLogsDataList: ArrayList<ActionLogsData>? = null
    private var assignToUserDataArrayList: ArrayList<AssignToUserData>? = null
    private var progressDialog: ProgressDialog? = null
    private var dataWorkList: DataWorkList? = null
    private var mAdapterAction: AdapterActionLogs? = null
    private var mTxtfileName: TextView? = null
    private var mTxtDateSche:TextView? = null
    private var mTxtWorkType: TextView? = null
    private var mTxtWorkComment:TextView? = null
    private var mTxtWorkDate:TextView? = null
    private var mtxtproduct:TextView? = null
    private var mTxtMsg:TextView? = null
    private var mEdtEarn: TextInputEditText? = null
    private  var mEdtExpenses:TextInputEditText? = null
    private  var mEdtComment:TextInputEditText? = null
    private var mSpnrassignTo: Spinner? = null
    private  var mSpnrStatus:Spinner? = null
    private val spinnerStat = arrayOf("Select Status", "Pending", "Closed", "Re-schedule")
    private var work_status = ""
    private var assignedUserId:kotlin.String? = ""
    private var work_id:kotlin.String? = null
    private var isSelected = false
    private val mCardLogs: CardView? = null
    private var mBtnUpdate: Button? = null
    private var mBtnUpdtAssign:android.widget.Button? = null
    private var mImgUpload: ImageView? = null
    private var mImage:ImageView? = null
    private var mImageCross:android.widget.ImageView? = null
    private var mBmp: Bitmap? = null
    private var mLinearImageView: LinearLayout? = null
    private var mLinearImageVer:LinearLayout? = null
    private var mLinearWorkDet:LinearLayout? = null
    private val currentView = 0
    private var stringArrayListImage: ArrayList<StorageFile>? = null
    private var destination: File? = null
    private var mRecyclImage: RecyclerView? = null
    private var mArraylistImages: ArrayList<Bitmap>? = null
    private var dateCallSchedule = ""
    private var mLinearCallSched: LinearLayout? = null
    private var calendar: Calendar? = null
    private var datePickerDialog: DatePickerDialog? = null
    var mGetWorkDet:GetWorkDataController?=null
    var mUpdateWorkController:UpdateWorkController?=null
    var mUpdateAsigneeCntroller:UpdateAsigneeController?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        var view=inflater.inflate(R.layout.fragment_tabs,container,false)

        mCardStatus = view!!.findViewById(R.id.card_status)
        mLinearWorkDet = view!!.findViewById(R.id.lin_work_det)
        mRelActionLogs = view!!.findViewById(R.id.rel_logs)
        mRecyclLogs = view!!.findViewById(R.id.recycl_actionLogs)
        mImgUpload = view!!.findViewById(R.id.img_uploadfiles)
        mLinearCallSched = view!!.findViewById(R.id.linear_date_CallSche)
        mTxtDateSche = view!!.findViewById<TextView>(R.id.txt_dt_callSche)

        mBtnUpdate = view!!.findViewById<View>(R.id.btn_update) as Button
        mLinearImageView = view!!.findViewById(R.id.linear_imageview)
        mLinearImageVer = view!!.findViewById(R.id.linear_verImg)
        mRecyclImage = view!!.findViewById(R.id.recycl_images)
        mTxtWorkType = view!!.findViewById<View>(R.id.txt_workType1) as TextView
        mTxtWorkComment = view!!.findViewById<View>(R.id.txt_workComment1) as TextView
        mTxtWorkDate = view!!.findViewById<View>(R.id.txt_workDate1) as TextView
        mtxtproduct = view!!.findViewById<View>(R.id.txt_workProd1) as TextView
        mTxtMsg = view!!.findViewById(R.id.txt_msg_workdet) as TextView
//        mTxtfileName = view!!.findViewById(R.id.txt_files) as TextView
        mEdtEarn = view!!.findViewById(R.id.edt_earnWork)
        mEdtExpenses = view!!.findViewById(R.id.edt_expensesWork)
        mEdtComment = view!!.findViewById(R.id.edt_commentsWork)
        mSpnrStatus = view!!.findViewById<Spinner>(R.id.spnr_statusWork)
        mSpnrassignTo = view!!.findViewById(R.id.spnr_assignToWork)
        mLinearAssign = view!!.findViewById(R.id.linear_assign)
        mBtnUpdtAssign = view!!.findViewById(R.id.btn_updt_assign)
        assignToUserDataArrayList = ArrayList<AssignToUserData>()
        //for image uploading
        stringArrayListImage = ArrayList<StorageFile>()
        mArraylistImages = ArrayList()
        progressDialog = context?.let { CommonUtilities.getDefaultLoader(it) }

        mLayoutmanager = LinearLayoutManager(activity)
        mRecyclLogs!!.setLayoutManager(mLayoutmanager)
        mRecyclLogs!!.setNestedScrollingEnabled(true)
        mRecyclLogs!!.setHasFixedSize(true)

        val mGridLayManager = GridLayoutManager(activity, 3)
        mRecyclImage!!.setLayoutManager(mGridLayManager)
        mRecyclImage!!.setNestedScrollingEnabled(true)
        mRecyclImage!!.setHasFixedSize(true)

        mGetWorkDet= GetWorkDataController(activity,this)
        mUpdateWorkController= UpdateWorkController(activity!!,this)
        mUpdateAsigneeCntroller= UpdateAsigneeController(activity!!,this)

        bundle = arguments
        if (bundle != null) {
            if (bundle!!.getString("flg").equals("0", ignoreCase = true)) {
                mCardStatus!!.setVisibility(View.VISIBLE)
                mLinearWorkDet!!.setVisibility(View.GONE)
                mRelActionLogs!!.setVisibility(View.GONE)
                mLinearAssign!!.setVisibility(View.GONE)
            } else if (bundle!!.getString("flg").equals("1", ignoreCase = true)) {
                mCardStatus!!.setVisibility(View.GONE)
                mLinearWorkDet!!.setVisibility(View.VISIBLE)
                mRelActionLogs!!.setVisibility(View.GONE)
                mLinearAssign!!.setVisibility(View.GONE)
            } else if (bundle!!.getString("flg").equals("2", ignoreCase = true)) {
                mCardStatus!!.setVisibility(View.GONE)
                mLinearWorkDet!!.setVisibility(View.GONE)
                mRelActionLogs!!.setVisibility(View.GONE)
                mLinearAssign!!.setVisibility(View.VISIBLE)
            } else if (bundle!!.getString("flg").equals("3", ignoreCase = true)) {
                mCardStatus!!.setVisibility(View.GONE)
                mLinearWorkDet!!.setVisibility(View.GONE)
                mRelActionLogs!!.setVisibility(View.VISIBLE)
                mLinearAssign!!.setVisibility(View.GONE)
            }
        }
        mImgUpload!!.setOnClickListener(View.OnClickListener {
            if (isStoragePermissionGranted()) {
                selectImage()
            }
        })
        mBtnUpdate!!.setOnClickListener(View.OnClickListener {
            if (mSpnrStatus!!.getSelectedItemPosition() == 0) {
                Toast.makeText(activity, getString(R.string.status), Toast.LENGTH_LONG)
                    .show()
            } else if (mEdtComment!!.getText().toString().equals("", ignoreCase = true)) {
                mEdtComment!!.setError("Please enter Comment")
                mEdtComment!!.requestFocus()
            } else {
                showLoader()
                mUpdateWorkController!!.updateWork(work_id,work_status,assignedUserId,mEdtEarn!!.text.toString(),mEdtExpenses!!.text.toString(),
                    mEdtComment!!.text.toString(),dateCallSchedule,stringArrayListImage!!)
            }
        })
        mBtnUpdtAssign!!.setOnClickListener(View.OnClickListener {
            if (mSpnrassignTo!!.getSelectedItem().toString().equals("Select", ignoreCase = true)) {
                Toast.makeText(activity, "Please select assignment", Toast.LENGTH_LONG)
                    .show()
            } else {
                showLoader()
                mUpdateAsigneeCntroller!!.updateAsignee(work_id!!,assignedUserId!!)
            }
        })

        mLinearCallSched!!.setOnClickListener(View.OnClickListener { datePickerdialog() })
        work_id = bundle!!.getString("work_id")
        showLoader()
        mGetWorkDet!!.getworkData(bundle!!.getString("work_id"))
        return view
    }

    //method for datepickerFrom dialog
    private fun datePickerdialog() {
        calendar = Calendar.getInstance()
        val year = calendar!!.get(Calendar.YEAR)
        val month = calendar!!.get(Calendar.MONTH)
        val dayOfMonth = calendar!!.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(
            activity,
            OnDateSetListener { datePicker, year, month, day ->
                dateCallSchedule = year.toString() + "/" + (month + 1) + "/" + day
                mTxtDateSche!!.text = day.toString() + "/" + (month + 1) + "/" + year
            }, year, month, dayOfMonth
        )
        datePickerDialog!!.getDatePicker().minDate = calendar!!.getTimeInMillis() - 1000
        datePickerDialog!!.show()
    }

    //method for runtime permissions
    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED || activity!!.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                //                Log.v(TAG,"Permission is granted");
                //                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_LONG).show();
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //            Log.v(TAG,"Permission is granted");
            Toast.makeText(activity, "Permission is granted", Toast.LENGTH_LONG).show()
            true
        }
    }

    /**
     * Choose the image
     */
    private fun selectImage() {
        val items: Array<String> =
            resources.getStringArray(R.array.str_array_option)
        val builder =
            AlertDialog.Builder(activity!!)
        builder.setTitle("Upload Image")
        builder.setCancelable(true)
        builder.setItems(items) { dialog, item ->
            if (item == 0) {
                //                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //                    intent.putExtra("android.intent.extra.quickCapture",true);
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 1)
            } else if (item == 1) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_PICK
                startActivityForResult(intent, 2)
            } else if (item == 2) {
                dialog.dismiss()
            }
        }
        builder.show()
    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val storageFile = StorageFile()
            if (requestCode == 1) {
                val thumbnail = data!!.extras["data"] as Bitmap
                val bytes = ByteArrayOutputStream()
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes)
                destination = File(
                    Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis().toString() + ".jpg"
                )
                val fos: FileOutputStream
                try {
                    destination!!.createNewFile()
                    fos = FileOutputStream(destination)
                    //                    selectedFile= destination.getAbsolutePath();
                    fos.write(bytes.toByteArray())
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                mBmp = thumbnail
                storageFile.setFileName(destination!!.getName())
                storageFile.setFilepath(destination!!.getAbsolutePath())
            } else {
                if (requestCode == 2) {
                    try {
                        val imageuri = data!!.data
                        val `is` =
                            activity!!.contentResolver.openInputStream(imageuri)
                        val bm = BitmapFactory.decodeStream(`is`)
                        val stream = ByteArrayOutputStream()
                        bm.compress(Bitmap.CompressFormat.PNG, 70, stream)
                        mBmp = bm
                        //                        mBmp = FilePath.rotateBitmap(bm);
                        //to get orientation
                        val file_1Str: String = FilePath.getPath(activity!!, imageuri)
                        //                        Bitmap orientation=FilePath.rotateImageIfRequired(bm,file_1Str);
//                        mBmp=orientation;
                        val f1 = File(file_1Str)
                        //imp
                        val f: File = FilePath.getCompressImagePath(f1)
                        val file2Str = f.name
                        storageFile.setFilepath(file_1Str)
                        storageFile.setFileName(file2Str)
                    } catch (e: Exception) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                }
            }
            if (stringArrayListImage == null) {
                stringArrayListImage = ArrayList()
            }
            stringArrayListImage!!.add(storageFile)
            //to add dynamic view for images
            AddImageViewdynamically(mBmp!!)
        }
    }

    //method to dynamically add Imageview
    private fun AddImageViewdynamically(mBmp: Bitmap) {
        mArraylistImages!!.add(mBmp)
        mLinearImageView!!.visibility = View.VISIBLE
        mRecyclImage!!.adapter = AdapterImages(activity!!, mArraylistImages!!)

    }


     //response for getWorkDet
    override fun onListFetchSuccess(dataWorkLists: DataWorkList?, actionLogsDataLists: ArrayList<ActionLogsData>?,
                                    assignToUserDataLists: ArrayList<AssignToUserData>?, flagAssigneeVisibility: String?,
        flagStatusVisibility: String?)
    {
        hideLoader()
        dataWorkList = dataWorkLists
        actionLogsDataList = actionLogsDataLists
        if (actionLogsDataList!!.size > 0) {
//            mAdapterAction.updateProducts(actionLogsDataList);
            mAdapterAction = AdapterActionLogs(activity!!, actionLogsDataList!!)
            mTxtMsg!!.visibility = View.GONE
        } else {
//            mAdapterAction.updateProducts(actionLogsDataList);
            mTxtMsg!!.visibility = View.VISIBLE
        }
        mRecyclLogs!!.adapter = mAdapterAction
        getData(dataWorkLists!!, assignToUserDataLists!!)
    }

    override fun onListFetchFailure(errorMsg: String?) {
        CommonUtilities.showToastOnMainThread(activity, errorMsg)

    }

    //method to get data
    private fun getData(
        dataWorkLists: DataWorkList,
        assignToUserDataArrayList: ArrayList<AssignToUserData>
    ) {
        //to set arrayadapter for status
        val a: ArrayAdapter<*> = ArrayAdapter<Any?>(
            activity,
            android.R.layout.simple_spinner_item,
            spinnerStat
        )
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        mSpnrStatus!!.adapter = a
        //to set the value coming from webservice
        if (dataWorkLists.workStatus.equals("0")) {
            mSpnrStatus!!.setSelection(1)
            work_status = "0"
            mLinearCallSched!!.visibility = View.GONE
        } else if (dataWorkLists.workStatus.equals("1")) {
            mSpnrStatus!!.setSelection(2)
            work_status = "1"
            mLinearCallSched!!.visibility = View.GONE
        } else {
            mSpnrStatus!!.setSelection(3)
            work_status = "2"
            mLinearCallSched!!.visibility = View.VISIBLE
        }
        //        int spinnerPosition = a.getPosition(dataWorkLists.getWorkStatus());
//        //set the default according to value
//        mSpnrStatus.setSelection(spinnerPosition);
        mSpnrStatus!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    if (isSelected) {
                        if (position == 1) {
                            work_status = "0"
                            mLinearCallSched!!.visibility = View.GONE
                        } else if (position == 2) {
                            work_status = "1"
                            mLinearCallSched!!.visibility = View.GONE
                        } else {
                            work_status = "2"
                            mLinearCallSched!!.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        mSpnrStatus!!.setOnTouchListener { v, event ->
            isSelected = true
            false
        }
        mTxtWorkType!!.text=(dataWorkLists.typeServiceSales)
        mTxtWorkComment!!.text=(dataWorkLists.comment)
        mTxtWorkDate!!.text=(dataWorkLists.callScheduleOndate)
        mtxtproduct!!.text=(dataWorkLists.productName)

        getAssignTo(assignToUserDataArrayList, dataWorkLists)

    }

    //method to get assign To
    private fun getAssignTo(
        assignToUserDataLists: ArrayList<AssignToUserData>,
        dataWorkLists: DataWorkList
    ) {
        if (!assignToUserDataArrayList!!.isEmpty()) {
            assignToUserDataArrayList!!.clear()
        }
        val assignToUserData = AssignToUserData()
        assignToUserData.setName("Select")
        assignToUserDataArrayList!!.add(assignToUserData)
        if (assignToUserDataLists.size > 0) {
            assignToUserDataArrayList!!.addAll(assignToUserDataLists)
            //to set arrayadapter for status
            val a: ArrayAdapter<*> = ArrayAdapter<Any?>(activity, android.R.layout.simple_spinner_item,
                assignToUserDataArrayList as List<Any?>?
            )
            a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpnrassignTo!!.adapter = a
            //Setting the ArrayAdapter data on the Spinner
            for (i in assignToUserDataArrayList!!.indices) {
                if (dataWorkLists.assignedToUserId != null) {
                    if (dataWorkLists.assignedToUserId
                            .equals(assignToUserDataArrayList!![i].getId())
                    ) {
                        // int spinnerPosition = a.getPosition(assignToUserDataArrayList.get(i).getName());
                        //set the default according to value
                        mSpnrassignTo!!.setSelection(i, true)
                    }
                }
            }
            mSpnrassignTo!!.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        if (isSelected) {
                            assignedUserId = assignToUserDataArrayList!![position].getId()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            mSpnrassignTo!!.setOnTouchListener { v, event ->
                isSelected = true
                false
            }
        }
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

   //response for update work
    override fun onUpdateFetchSuccess(msg: String, msg_string: String) {
       hideLoader()
       if(msg.equals("1"))
       {
           Toast.makeText(activity,msg_string,Toast.LENGTH_LONG).show()
           activity!!.onBackPressed()
       }else
       {
           Toast.makeText(activity,msg_string,Toast.LENGTH_LONG).show()
       }
    }

    override fun onUpdateFetchFailure(errorMsg: String?) {
        CommonUtilities.showToastOnMainThread(activity,errorMsg)
    }

    override fun onUpdateAsigneeSuccess(msg: String, msg_string: String) {
        hideLoader()
        if(msg.equals("1"))
        {
            Toast.makeText(activity,msg_string,Toast.LENGTH_LONG).show()
        }else
        {
            Toast.makeText(activity,msg_string,Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpdateAsigneeFailure(errorMsg: String) {
      CommonUtilities.showToastOnMainThread(activity,errorMsg)
    }
}