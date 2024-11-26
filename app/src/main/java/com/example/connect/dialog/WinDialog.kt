package com.example.connect.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import com.example.desktopdemo.databinding.WinDialogBinding

class WinDialog(context: Context) : AppCompatDialog(context) {

    private var mBinding: WinDialogBinding = WinDialogBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

    }

    override fun onStart() {
        super.onStart()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window?.setGravity(Gravity.CENTER)
    }

    fun setTitle(title: String) {
        mBinding.winTitle.text = title
    }

    fun setContent(content: String) {
        mBinding.content.text = content
    }
}