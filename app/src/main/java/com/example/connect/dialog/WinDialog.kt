package com.example.connect.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.connect.activity.MainActivity
import com.example.connect.activity.Start_Board
import com.example.desktopdemo.R
import com.example.desktopdemo.databinding.WinDialogBinding

class WinDialog(context: Context) : Dialog(context), View.OnClickListener {


    private lateinit var mBinding: WinDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = WinDialogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        mBinding.restart.setOnClickListener(this)
        mBinding.quit.setOnClickListener(this)
    }

    fun setWinTitle(s: String?) {
        mBinding.content.text = s
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.restart -> {
                val intent1 = Intent(
                    context,
                    Start_Board::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent1)
                cancel()
            }

            R.id.quit -> {
                val intent2 = Intent(
                    context,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent2)
                cancel()
            }

        }
    }
}