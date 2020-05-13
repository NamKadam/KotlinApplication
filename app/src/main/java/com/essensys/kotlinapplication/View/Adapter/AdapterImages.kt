package com.essensys.kotlinapplication.View.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.essensys.kotlinapplication.R
import java.util.*

class AdapterImages(
    private val mContext: Context,
    private val mArraylistImages: ArrayList<Bitmap>
) :
    RecyclerView.Adapter<AdapterImages.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_custom_image, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mImgUpload: ImageView
        val mImgCross: ImageView

        init {
            mImgUpload = itemView.findViewById(R.id.img)
            mImgCross = itemView.findViewById(R.id.img_cross)
        }
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.mImgUpload.setImageBitmap(mArraylistImages[position])
        holder.mImgCross.setOnClickListener { //                mLinearImageView.removeView((View) v.getParent());
            //                currentView--;
            mArraylistImages.removeAt(position)
            notifyDataSetChanged()
            notifyItemRemoved(position)
        }
    }

    //    public void updateProducts(ArrayList<ActionLogsData> actionLogsDataList) {
    //        this.mArraylistLogs = actionLogsDataList;
    //    }
    override fun getItemCount(): Int {
        return mArraylistImages.size
    }

}

