package com.taxi.apnisawari

import android.app.ProgressDialog
import android.content.Context

class AppUtils {

    companion object {

        var mProgressDialog: ProgressDialog? = null

        fun showProgressBar(context: Context, title: String, message: String) {
            mProgressDialog = ProgressDialog(context)
            mProgressDialog?.setTitle("Please wait")
            mProgressDialog?.setMessage(message)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.show()
        }

        fun hideprogressbar() {
            if (mProgressDialog != null)
                mProgressDialog?.dismiss()
        }
    }
}