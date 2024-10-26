package com.example.connect.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.connect.MainActivity
import com.example.desktopdemo.R
import com.example.connect.Start_Board

class WinDialog : AlertDialog, View.OnClickListener {
    private var mContext: Context? = null
    private var title: String? = null

    constructor(context: Context?) : super(context) {
        mContext = context
    }

    constructor(
        context: Context?,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    protected constructor(context: Context?, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_dialog)
        init()
    }

    private fun init() {
//        View win = findViewById(R.id.winDialog);
//        win.getBackground().setAlpha(20);
        val restart = findViewById<Button>(R.id.restart)
        val quit = findViewById<Button>(R.id.quit)
        val winTitle = findViewById<TextView>(R.id.win_title)
        restart.setOnClickListener(this)
        quit.setOnClickListener(this)
        winTitle.text = title
    }

    fun setWinTitle(s: String?) {
        title = s
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.restart -> {
                val intent1 = Intent(
                    mContext,
                    Start_Board::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                mContext!!.startActivity(intent1)
                cancel()
            }

            R.id.quit -> {
                val intent2 = Intent(
                    mContext,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                mContext!!.startActivity(intent2)
                cancel()
            }

        }
    }
}