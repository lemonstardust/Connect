package com.example.connect.observe


interface IChessChangeListener {

    fun onChessCountChange(curSize:Int)

    fun onRoleChange(c: Char)
}