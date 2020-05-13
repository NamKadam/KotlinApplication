package com.essensys.kotlinapplication.View.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.essensys.kotlinapplication.Model.ActionLogsData
import com.essensys.kotlinapplication.Model.DocumentListData
import com.essensys.kotlinapplication.R
import com.github.chrisbanes.photoview.PhotoView
import java.lang.Exception
import java.util.*

class AdapterActionLogs(
    private val mContext: Context,
    mArraylistLogs: ArrayList<ActionLogsData>
) :
    RecyclerView.Adapter<AdapterActionLogs.MyViewHolder>() {
    private var mArraylistLogs: ArrayList<ActionLogsData>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_action_logs, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mTxtActionDate: TextView
        val mTxtWorkDt: TextView
        val mTxtActionBy: TextView
        val mTxtActionStatus: TextView
        val mTxtActionComment: TextView
        val mTxtMsg: TextView
        val mImgdocList: ImageView
        val mLinearDoc: LinearLayout
        val mLinearMain: LinearLayout

        init {
            mTxtActionDate = itemView.findViewById(R.id.tvActionDate)
            mTxtWorkDt = itemView.findViewById(R.id.tv_workDt)
            mTxtActionBy = itemView.findViewById(R.id.tvActionBy)
            mTxtActionStatus = itemView.findViewById(R.id.tv_statusLogs)
            mTxtActionComment = itemView.findViewById(R.id.tvCommentLogs)
            mImgdocList = itemView.findViewById(R.id.img_doc_list)
            mLinearDoc = itemView.findViewById(R.id.linear_docList)
            mLinearMain = itemView.findViewById(R.id.lin_main_doc)
            mTxtMsg = itemView.findViewById(R.id.txt_msg_err)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val actionLogsData: ActionLogsData = mArraylistLogs[position]
        holder.mTxtActionDate!!.text=actionLogsData.getActionOnDateTime()
        holder.mTxtActionBy.setText(actionLogsData.getName())
        holder.mTxtWorkDt.setText(actionLogsData.getCallScheduleOndate())
        holder.mTxtActionComment.setText(actionLogsData.getComment())
        if (actionLogsData.getStatus().equals("0")) {
            holder.mTxtActionStatus.text = "Pending"
            holder.mTxtActionStatus.setTextColor(
                mContext.resources.getColor(R.color.colorGreen)
            )
        } else if (actionLogsData.getStatus().equals("1")) {
            holder.mTxtActionStatus!!.text = "Closed"
            holder.mTxtActionStatus.setTextColor(mContext.resources.getColor(R.color.reddish))
        } else {
            holder.mTxtActionStatus.text = "Re-Schedule"
            holder.mTxtActionStatus.setTextColor(
                mContext.resources.getColor(R.color.colorBlack)
            )
        }
        holder.mImgdocList.setOnClickListener {
            if (actionLogsData.getDocumentList()!!.size > 0) {
                for (i in 0 until actionLogsData.getDocumentList()!!.size) {
                    val documentListData1 = DocumentListData()
                    documentListData1.setFileName(
                        actionLogsData.getDocumentList()!!.get(i)!!.getFileName()
                    )
                    documentListData1.setFilepath(actionLogsData.getDocumentList()!!.get(i)!!.getFilepath()
                    )

                    // Add the text layout to the parent layout
                    var inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    var view: View = inflater.inflate(R.layout.lay_doc_list, null)
                    // In order to get the view we have to use the new view with text_layout in it
                    var textView1 = view.findViewById<View>(R.id.txtSrNo) as TextView
                    var mTxtOpen = view.findViewById<View>(R.id.txt_open) as TextView
//                    textView1.setText(i + 1 + ")")
                    try {
                        var cnt=i+1
                        textView1.setText(cnt.toString()+")")

                    }catch (e:Exception)
                    {
                      e.printStackTrace()
                    }
                    var textView2 = view.findViewById<View>(R.id.txt_filename) as TextView
                    textView2.setText(actionLogsData.getDocumentList()!!.get(i)!!.getOriginalFileName())

                    mTxtOpen.setOnClickListener {
                        var dialog = Dialog(mContext, R.style.ThemeTransparentDialog)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.layout_image)
                        dialog.setCancelable(true)
                        val pbar = dialog.findViewById<ProgressBar>(R.id.pbBar)
                        val photoView: PhotoView = dialog.findViewById(R.id.img_pic)
                        val mImgcancel =
                            dialog.findViewById<ImageView>(R.id.img_can_pic)
                        //                                    photoView.setImageResource(R.drawable.solonox_logo);
                        Glide.with(mContext)
                            .load(documentListData1.getFilepath())
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    pbar.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    pbar.visibility = View.GONE
                                    return false
                                }
                            })
                            .into(photoView)
                        mImgcancel.setOnClickListener { dialog.dismiss() }
                        dialog.show()
                    }
                    // Add the text view to the parent layout
                    holder.mLinearDoc.addView(view)
                }
                if (holder.mLinearMain.visibility == View.VISIBLE) {
                    holder.mLinearDoc.visibility = View.GONE
                    holder.mTxtMsg.visibility = View.GONE
                    holder.mLinearMain.visibility = View.GONE
                    //                        if (holder.mLinearDoc.getParent() != null)
                    holder.mLinearDoc.removeAllViews()
                    //                            ((ViewGroup) holder.mLinearDoc.getParent()).removeView(holder.mLinearDoc);
                } else {
                    holder.mLinearMain.visibility = View.VISIBLE
                    holder.mLinearDoc.visibility = View.VISIBLE
                    holder.mTxtMsg.visibility = View.GONE
                }
            } else {
                //
                if (holder.mLinearMain.visibility == View.VISIBLE) {
                    holder.mLinearDoc.visibility = View.GONE
                    holder.mTxtMsg.visibility = View.GONE
                    holder.mLinearMain.visibility = View.GONE
                } else {
                    holder.mLinearMain.visibility = View.VISIBLE
                    holder.mLinearDoc.visibility = View.GONE
                    holder.mTxtMsg.visibility = View.VISIBLE
                }
            }
        }
    }

    fun updateProducts(actionLogsDataList: ArrayList<ActionLogsData>) {
        mArraylistLogs = actionLogsDataList
    }

    override fun getItemCount(): Int {
        return mArraylistLogs.size
    }

    //imp for onscroll of recyclerview
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    init {
        this.mArraylistLogs = mArraylistLogs
    }
}
