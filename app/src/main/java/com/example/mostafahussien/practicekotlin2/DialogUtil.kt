package com.example.mostafahussien.practicekotlin2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable


object DialogUtil {

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog=Dialog(context)
        progressDialog.show()
        if (progressDialog.window != null)
            progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            return progressDialog
    }
}