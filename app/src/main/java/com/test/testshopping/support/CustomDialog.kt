package com.test.testshopping.support

import android.app.Dialog
import android.content.Context
import com.test.testshopping.databinding.LoadingDialogLayBinding

object CustomDialog {

    fun loadingDialog(context: Context): Dialog {

        val loadingDialog =
            Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.setCancelable(false)
        loadingDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val dialogBinding = LoadingDialogLayBinding.inflate(loadingDialog.layoutInflater)
        dialogBinding.run {
            loadingDialog.setContentView(dialogBinding.root)
        }

        return loadingDialog
    }
}