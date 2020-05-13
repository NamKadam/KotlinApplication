package com.essensys.kotlinapplication.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.View.Activity.ActivityCollapsible
import java.util.*

class AdapterCustManagement : RecyclerView.Adapter<AdapterCustManagement.MyViewHolder> {
    var dataWorkLists: ArrayList<DataWorkList>? = null
    var mContext: Context? = null


    constructor(
        mContext: Context?,
        dataWorkLists: ArrayList<DataWorkList>?
    ) {
        this.mContext = mContext
        this.dataWorkLists = dataWorkLists
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_custman_list, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ResourceType", "NewApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataWorkList: DataWorkList = this.dataWorkLists!!.get(position)

        holder.mTxtName.text=dataWorkList.name
        holder.mTxtMobNo.text=dataWorkList.mobile
        holder.mTxtProd.text=dataWorkList.productName
        holder.mTxtCity.text=dataWorkList.cityName
        holder.mTxtAssigneeName.visibility=View.VISIBLE
        if (dataWorkList.assignToName.equals("")) {
            holder.mTxtAssigneeName.text="NA"
        } else {
            holder.mTxtAssigneeName.text=
                dataWorkList.assignToName.toString() + " ( " + dataWorkList.assignToNameAdminType + " )"

            //
        }
        if (dataWorkList.email.equals("")) {
            holder.mRelMail.visibility=View.GONE
        } else {
            holder.mRelMail.visibility=View.VISIBLE
            holder.mTxtMail.text=dataWorkList.email
        }
        if (dataWorkList.workStatus.equals("0")) {
            holder.mBtnStatus.text=dataWorkList.customerStatus
            holder.mBtnStatus.background=
                mContext!!.resources.getDrawable(R.drawable.drawable_status_pend)

            holder.mBtnStatus.text="Pending"
        } else if (dataWorkList.workStatus.equals("1")) {
            holder.mBtnStatus.text=dataWorkList.customerStatus
            holder.mBtnStatus.background=
                mContext!!.resources.getDrawable(R.drawable.drawable_status_closed)

            holder.mBtnStatus.setText("Closed")
        } else if (dataWorkList.workStatus.equals("2")) {
            holder.mBtnStatus.text=dataWorkList.customerStatus
            holder.mBtnStatus.background=
                mContext!!.resources.getDrawable(R.drawable.drawable_status_resch)
            holder.mBtnStatus.text="Re-Schedule"
        } else {
            holder.mBtnStatus.visibility=View.GONE
        }
        holder.mImgedit.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, ActivityCollapsible::class.java)
            intent.putExtra("work_id", dataWorkLists!![position].workId)
            mContext!!.startActivity(intent)
        })
    }

    fun updateProducts(dataWorkListArrayList: ArrayList<DataWorkList>?) {
        this.dataWorkLists = dataWorkListArrayList!!
        this.notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mTxtName: TextView
        var mTxtMobNo: TextView
        var mTxtProd: TextView
        var mTxtCity: TextView
        var mTxtAssigneeName: TextView
        var mTxtMail: TextView
        var mImgedit: ImageView
        var mBtnStatus: Button
        var mRelMail: RelativeLayout

        init {
            mTxtName = itemView.findViewById(R.id.txt_name)
            mTxtMobNo = itemView.findViewById(R.id.txt_mob)
            mTxtProd = itemView.findViewById(R.id.txt_prod)
            mTxtCity = itemView.findViewById(R.id.txt_city)
            mTxtAssigneeName = itemView.findViewById(R.id.txt_custName)
            mImgedit = itemView.findViewById(R.id.img_edit)
            mTxtMail = itemView.findViewById(R.id.txt_mail)
            mRelMail = itemView.findViewById(R.id.rel_mail)
            mBtnStatus = itemView.findViewById(R.id.btn_status)
        }
    }

    override fun getItemCount(): Int {
        return dataWorkLists!!.size
    }
}
