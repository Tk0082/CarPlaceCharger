package com.betrendmobile.carplacecharger

import android.content.Context
import android.widget.Toast

class ShowMessage(private val context: Context, private val msg: String) {
    fun showToast(){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}