package com.essensys.kotlinapplication.utils

import android.app.Application
import android.content.Context
import com.essensys.kotlinapplication.Model.User


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //Fabric.with(this, new Crashlytics());
    }

    companion object {

        private var currentUser: User? = null

        fun setCurrentUser(currentUser: User?) {

            App.currentUser = currentUser
        }

        fun getCurrentUser(mContext: Context?): User? {
            if (currentUser == null) {
                currentUser = SharedPreferenceManager.with(mContext!!).loggedInUser
            }
            return currentUser
        }

        fun isUserLoggedIn(mContext: Context): Boolean {
            return getCurrentUser(mContext) != null
        }
    }


}
