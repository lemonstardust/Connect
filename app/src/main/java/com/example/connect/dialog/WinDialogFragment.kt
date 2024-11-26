package com.example.connect.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.connect.activity.MainActivity
import com.example.connect.activity.StartBoard
import com.example.desktopdemo.R
import com.example.desktopdemo.databinding.WinDialogBinding

class WinDialogFragment : DialogFragment(),
    View.OnClickListener {


    private lateinit var mBinding: WinDialogBinding


    companion object {

        fun show(fragmentManager: FragmentManager, bundle: Bundle? = null) {
            val fragment = WinDialogFragment()
            fragment.arguments = bundle
            Log.e("TAG", "fragemnt tag = ${fragment.tag}")
            fragmentManager.beginTransaction().add(fragment, WinDialogFragment::class.java.simpleName)
                .commit()
        }

        fun close(fragmentManager: FragmentManager) {
            val fragment =
                fragmentManager.findFragmentByTag(WinDialogFragment::class.java.simpleName) ?: return
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = WinDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mBinding.restart.setOnClickListener(this)
        mBinding.quit.setOnClickListener(this)
    }

    fun setWinTitle(s: String) {
        mBinding.content.text = s
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.restart -> {
                val intent1 = Intent(
                    context,
                    StartBoard::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent1)
                dismiss()
            }

            R.id.quit -> {
                val intent2 = Intent(
                    context,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent2)
                dismiss()
            }

        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }


//    override fun show() {
//        super.show()
////        window?.setBackgroundDrawableResource(android.R.color.transparent)
//        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window?.setGravity(Gravity.CENTER)
//    }


}