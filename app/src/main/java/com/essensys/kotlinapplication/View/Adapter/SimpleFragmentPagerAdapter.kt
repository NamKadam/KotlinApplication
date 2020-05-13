package com.essensys.kotlinapplication.View.Adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.View.Fragment.FragmentTabs

class SimpleFragmentPagerAdapter(
    private val mContext: Context,
    fm: FragmentManager?,
    private val work_id: String,
    private val flagAssigneeVisibility: String,
    private val flagStatusVisible: String
) :
    FragmentPagerAdapter(fm!!) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (flagAssigneeVisibility.equals("true", ignoreCase = true)) {
            if (position == 0) {  //status
                val bundle = Bundle()
                bundle.putString("flg", "0")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 1) { //details
                val bundle = Bundle()
                bundle.putString("flg", "1")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 2) { //assignment
                val bundle = Bundle()
                bundle.putString("flg", "2")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 3) { //action Logs
                val bundle = Bundle()
                bundle.putString("flg", "3")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            }
        } else if (flagAssigneeVisibility.equals(
                "false",
                ignoreCase = true
            ) && flagStatusVisible.equals("false", ignoreCase = true)
        ) {
            if (position == 0) { //Details
                val bundle = Bundle()
                bundle.putString("flg", "1")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 1) { //action Logs
                val bundle = Bundle()
                bundle.putString("flg", "3")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            }
        } else {
            if (position == 0) {  //status
                val bundle = Bundle()
                bundle.putString("flg", "0")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 1) { //details
                val bundle = Bundle()
                bundle.putString("flg", "1")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            } else if (position == 2) {   //action Logs
                val bundle = Bundle()
                bundle.putString("flg", "3")
                bundle.putString("work_id", work_id)
                fragment = FragmentTabs()
                fragment!!.arguments = bundle
                return fragment
            }
        }
        return fragment!!
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return if (flagAssigneeVisibility.equals("true", ignoreCase = true)) {
            4
        } else if (flagStatusVisible.equals("false", ignoreCase = true)) {
            2
        } else {
            3
        }
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return if (flagAssigneeVisibility.equals("true", ignoreCase = true)) {
            when (position) {
                0 -> mContext.getString(R.string.Status)
                1 -> mContext.getString(R.string.WorkDet)
                2 -> mContext.getString(R.string.Assign)
                3 -> mContext.getString(R.string.ActionLogs)
                else -> null
            }
        } else if (flagAssigneeVisibility.equals(
                "false",
                ignoreCase = true
            ) && flagStatusVisible.equals("false", ignoreCase = true)
        ) {
            when (position) {
                0 -> mContext.getString(R.string.WorkDet)
                1 -> mContext.getString(R.string.ActionLogs)
                else -> null
            }
        } else {
            when (position) {
                0 -> mContext.getString(R.string.Status)
                1 -> mContext.getString(R.string.WorkDet)
                2 -> mContext.getString(R.string.ActionLogs)
                else -> null
            }
        }
    }

}