package com.essensys.kotlinapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.essensys.kotlinapplication.Model.User
import com.essensys.kotlinapplication.remote.Constant

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPreferenceManager
/**
 * Private constructor for instantiating the singleton.
 */
private constructor(mContext: Context) {
    private val mContext: Context
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val isLoggingOut: Boolean = false

    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunchWaterSupply"

    var isUserLoggedIn: Boolean
        get() = sharedPreferences!!.getBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, false)
        set(isUserLoggedIn) {
            editor!!.putBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, isUserLoggedIn)
            editor!!.commit()
        }


    val loggedInUser: User?
        get() {
            val json =
                sharedPreferences!!.getString(Constant.SharedPreferences.LOGGED_IN_USER, null)
                    ?: return null
            return Gson().fromJson<User>(json, object : TypeToken<User>() {

            }.type)
        }

    var isFirstTimeLaunch: Boolean
        get() = sharedPreferences!!.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor!!.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor!!.commit()
        }

    fun logout(mContext: Context) {
        if (INSTANCE == null) {
            this.sharedPreferences = mContext.getSharedPreferences(
                Constant.Common.SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        }
        App.setCurrentUser(null)
        this.editor = this.sharedPreferences!!.edit()
        this.editor!!.clear()
        this.editor!!.commit()

    }

    init {
        //It is important to store the application context
        //in order to avoid memory leaks.
        this.mContext = mContext.applicationContext
        this.sharedPreferences = mContext.getSharedPreferences(
            Constant.Common.SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        this.editor = sharedPreferences!!.edit()
    }


    fun updateLoggedInUser(user: User?) {
        if (user == null) {
            editor!!.remove(Constant.SharedPreferences.LOGGED_IN_USER)
        } else {
            editor!!.putString(Constant.SharedPreferences.LOGGED_IN_USER, Gson().toJson(user))
        }
        editor!!.commit()
    }

    companion object {
        private var INSTANCE: SharedPreferenceManager? = null

        fun with(mContext: Context): SharedPreferenceManager {
            if (INSTANCE == null) {
                INSTANCE = SharedPreferenceManager(mContext)
            }
            return INSTANCE as SharedPreferenceManager
        }
    }

}
