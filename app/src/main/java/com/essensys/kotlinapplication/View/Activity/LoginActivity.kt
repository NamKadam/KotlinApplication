package com.essensys.kotlinapplication.View.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.essensys.kotlinapplication.Controller.LoginControler
import com.essensys.kotlinapplication.Model.User
import com.essensys.kotlinapplication.R
import com.essensys.kotlinapplication.ViewModel.LoginVewModel
import com.essensys.kotlinapplication.utils.App
import com.essensys.kotlinapplication.utils.CommonUtilities
import com.essensys.kotlinapplication.utils.SharedPreferenceManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(),View.OnClickListener,LoginControler.StatusListener {

    private var etUsername: TextInputEditText? = null
    private var etPassword:TextInputEditText? = null
    private var progressDialog: ProgressDialog? = null
    private var isViewPass = false
    private var imgPasswordStatus: ImageView? = null
    private var mBtnLogin:AppCompatButton?=null
    private var mTxtForPass:AppCompatTextView?=null
    private var mImgPass:ImageView?=null
    private var mLoginCntroller: LoginControler?=null
    var loginViewModel:LoginVewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

    }
    //initialization of UI
    private fun initUI() {
        etUsername=findViewById(R.id.etMobile)
        etPassword=findViewById(R.id.etPassword)
        imgPasswordStatus=findViewById(R.id.imgPasswordStatus)
        mBtnLogin=findViewById(R.id.btnLogin)
        mTxtForPass=findViewById(R.id.tvForgotPassword)
        mImgPass=findViewById(R.id.imgPasswordStatus)
        mBtnLogin!!.setOnClickListener(this)
        mTxtForPass!!.setOnClickListener(this)
        mImgPass!!.setOnClickListener(this)
        progressDialog=CommonUtilities.getDefaultLoader(this)
        mLoginCntroller=LoginControler(this,this)
        if (App.isUserLoggedIn(this)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        // View Model
        loginViewModel = ViewModelProviders.of(this).get(LoginVewModel::class.java)
    }

    override fun onClick(view: View?) {
       when(view?.id)
           {
           R.id.btnLogin ->
               if (!CommonUtilities.isValidEmail(etUsername!!.text.toString()))
           {
               Toast.makeText(this,"Please enter valid email",Toast.LENGTH_LONG).show()
           } else if (etPassword!!.text.toString().equals("", ignoreCase = true)) {

                   Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
               } else {
//                   doLogin(
//                       etUsername!!.text.toString().trim { it <= ' ' },
//                       etPassword!!.text.toString().trim { it <= ' ' })
                   showLoader()
//                   mLoginCntroller!!.doLogin( etUsername!!.text.toString().trim { it <= ' ' },
//                       etPassword!!.text.toString().trim { it <= ' ' })

                   //MVVM
                   GlobalScope.launch (Dispatchers.Main) {
                       async {
                           CallLoginWebservice()
                       }
                   }

               }
           R.id.imgPasswordStatus ->
                   visibilityPassword()
           R.id.tvForgotPassword ->
           forgotPasswordDialog()
       }
    }
    //webservice to call login
    private suspend fun CallLoginWebservice() {
        loginViewModel!!.getLoginResponseLiveData(etUsername!!.text.toString().trim (),
            etPassword!!.text.toString().trim ())
            .observe(this, Observer { response->
            if(response!=null)
            {
                val strResponse: User = response
                if(strResponse.getMsg().equals("1"))
                {
                    Toast.makeText(this, "Logged in Successfully.", Toast.LENGTH_SHORT).show()
                    try{
                        SharedPreferenceManager.with(this).updateLoggedInUser(strResponse)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else
                {
                    Toast.makeText(this, "Login Failed. try again.", Toast.LENGTH_SHORT)
                        .show()
                }

                // progressBar.setVisibility(View.GONE);
                //  List<Article> articles = articleResponse.getArticles();
                //  articleArrayList.addAll(articles);
                //   adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Login Failed. try again.", Toast.LENGTH_SHORT)
                    .show()
            }
            hideLoader()
        })

    }

    private fun showLoader()
    {
        if (progressDialog == null || progressDialog!!.isShowing()) {
            return;
        }
        Handler (Looper.getMainLooper()).post{
            progressDialog!!.show()
            }

    }

    private fun hideLoader() {
        if (progressDialog == null || !progressDialog!!.isShowing()) {
            return
        }
        Handler(Looper.getMainLooper()).post { progressDialog!!.dismiss() }
    }

//dialog forforgotPass
    fun forgotPasswordDialog() {
        val dialog = Dialog(this, R.style.ThemeTransparentDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog.setContentView(R.layout.layout_forgot_password)
//        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);
//
//        dialog.getWindow().setLayout(width, height);
        dialog.setCancelable(true)
//        dialog.setCanceledOnTouchOutside(true);
        // set the custom dialog components - text, image and button
        val etForgotEmail = dialog.findViewById(R.id.etForgotEmail) as TextInputEditText
        val img_cancel = dialog.findViewById(R.id.img_cancel) as ImageView
        img_cancel.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        val dialogButton = dialog.findViewById(R.id.btnSubmit) as AppCompatButton
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(View.OnClickListener {
            if (!CommonUtilities.isValidEmail(etForgotEmail.getText().toString())) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter valid email id",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dialog.dismiss()
                mLoginCntroller!!.forgotPassword(etForgotEmail.getText().toString())
            }
        })
        dialog.show()
    }

    fun visibilityPassword()
    {
       if (isViewPass) {
           isViewPass = false
           etPassword!!.setTransformationMethod(PasswordTransformationMethod.getInstance())
           imgPasswordStatus!!.setImageResource(R.drawable.ic_visibility_black_24dp)
       } else  {
           isViewPass = true
           // show password
           etPassword!!.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
           imgPasswordStatus!!.setImageResource(R.drawable.ic_visibility_off_black_24dp)

       }
    }

    override fun onListFetchSuccess(mContext: Context?, msg: String?, loginData: User) {
        hideLoader()
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
        try {
            SharedPreferenceManager.with(mContext!!).updateLoggedInUser(loginData)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onListFetchFailure(errorMsg: String?) {
        CommonUtilities.showToastOnMainThread(this,errorMsg)
    }


}
