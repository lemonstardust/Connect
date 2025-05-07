package com.example.nativelib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nativelib.databinding.ActivityNativeLibBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNativeLibBinding

    private val mNativeLib by lazy { NativeLib() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNativeLibBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        mBinding.apply {
            init.setOnClickListener {

            }
        }
    }


}