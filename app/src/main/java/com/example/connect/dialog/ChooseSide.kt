package com.example.connect.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.connect.game.GameConfig
import com.example.desktopdemo.R
import com.example.desktopdemo.databinding.ChooseFirstOrSecondDialogBinding

class ChooseSide(context: Context) : Dialog(context), View.OnClickListener {


    private lateinit var mBinding: ChooseFirstOrSecondDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ChooseFirstOrSecondDialogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }


    private fun init() {


        setCancelable(false)
        setCanceledOnTouchOutside(false)

        mBinding.blackSide.setOnClickListener(this)
        mBinding.whiteSide.setOnClickListener(this)
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