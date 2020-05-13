package com.essensys.kotlinapplication.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity


import com.essensys.kotlinapplication.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

object CommonUtilities {
    fun getRandomNumber(max: Int): Int {
        val rand = Random()
        return rand.nextInt(max) + 1
    }

    fun getAppVersionName(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return "Version $version"
    }

    fun showToastOnMainThread(mContext: Context?, msg: String?) {
        if (mContext == null) {
            return
        }
        val appContext = mContext.applicationContext ?: return
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun showToastOnMainThreadSuccess(mContext: Context?, msg: String) {
        if (mContext == null) {
            return
        }
        val appContext = mContext.applicationContext ?: return
        Handler(Looper.getMainLooper()).post {
            val mToast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT)
            mToast.view.setBackgroundColor(mContext.resources.getColor(R.color.colorGreen))
            mToast.show()
        }
    }

    fun openFragmentContainerActivityWithDelay(
        mContext: Context,
        navViewId: Int,
        navItemIndex: Int
    ) {
        //        Intent intent = new Intent(mContext, DashBoardActivity.class);
        //        intent.putExtra(Constant.IntentExtras.NAV_VIEW_ID, navViewId);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        mContext.startActivity(intent);

    }

    fun openFragmentContainerActivityWithDelay(
        mContext: Context,
        navViewId: Int,
        navItemIndex: Int, data: String
    ) {
        //        Intent intent = new Intent(mContext, DashBoardActivity.class);
        //        intent.putExtra(Constant.IntentExtras.NAV_VIEW_ID, navViewId);
        //        intent.putExtra(Constant.IntentExtras.DATA, data);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        mContext.startActivity(intent);

    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getDefaultLoader(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setMessage(context.getString(R.string.loading))
        return progressDialog
    }

    fun convertDateFormat(
        strDate: String,
        strInputDateFormat: String,
        strOutputDateFormat: String
    ): String {
        var formattedDate: String
        try {
            val sdfInput = SimpleDateFormat(strInputDateFormat, Locale.US)
            val sdfOutput = SimpleDateFormat(strOutputDateFormat, Locale.US)
            formattedDate = sdfOutput.format(sdfInput.parse(strDate)!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            formattedDate = strDate
        }

        Log.e(
            "==formattedDate==",
            "$formattedDate===strInputDateFormat===$strInputDateFormat===strOutputDateFormat===$strOutputDateFormat"
        )
        return formattedDate
    }

}
