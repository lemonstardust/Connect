package com.example.connect.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.connect.game.GameConfig
import com.example.desktopdemo.R

class ChooseSide : AlertDialog, View.OnClickListener {
    private var mContext: Context? = null

    constructor(context: Context?) : super(context) {
        mContext = context
    }

    constructor(
        context: Context?,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_first_or_second_dialog)
        init()
    }

    private fun init() {
        val black_side = findViewById<Button>(R.id.black_side)
        val white_side = findViewById<Button>(R.id.white_side)
        black_side.setOnClickListener(this)
        white_side.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.black_side -> {
                GameConfig.BlackStatus = GameConfig.PLAYER
                GameConfig.WhiteStatus = GameConfig.AI
                cancel()
            }

            R.id.white_side -> {
                GameConfig.WhiteStatus = GameConfig.PLAYER
                GameConfig.BlackStatus = GameConfig.AI
                cancel()
            }
        }
    }
}