package com.essensys.kotlinapplication.Interfaces

import android.content.Context
import com.essensys.kotlinapplication.Model.User

interface loginInterface {

    fun onListFetchSuccess(
        mContext: Context?,
        msg: String?,
        loginData: User
    )
    fun onListFetchFailure(errorMsg: String?)
}