package com.example.mostafahussien.practicekotlin2.UI.Base

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.mostafahussien.practicekotlin2.DialogUtil

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
open class BaseActivity : AppCompatActivity() , BaseView {

    private var progressDialog:Dialog?=null

    fun hasPermmision(permissions: Array<String>) : Boolean{
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        return permissions.none{
            ContextCompat.checkSelfPermission(this,it) !=PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = DialogUtil.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
        }
    }

    override fun showMessage(message: String) {
        if (message != null)
            displayMessage(message)
    }

    override fun showError(message: String) {
        if (message != null)
            displayError(message)
    }

}