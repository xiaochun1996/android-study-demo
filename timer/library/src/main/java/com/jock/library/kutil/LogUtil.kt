package com.jock.library.kutil

import android.util.Log

/**
 * Created by lxc on 1/9/22 2:04 PM.
 * Desc: 日志相关
 */

object LogUtil{

    var default = ""

    private const val before = "jock::"

    fun v(value: String){
        v(default,value)
    }

    fun v(tag:String,value:String){
        Log.v("$before$tag",value)
    }

    fun d(value: String){
        d(default,value)
    }

    fun d(tag:String,value:String){
        Log.d("$before$tag",value)
    }

    fun i(value: String){
        i(default,value)
    }

    fun i(tag:String,value:String){
        Log.i("$before$tag",value)
    }

    fun w(value: String){
        w(default,value)
    }

    fun w(tag:String,value:String){
        Log.w("$before$tag",value)
    }

    fun e(value: String){
        e(default,value)
    }

    fun e(tag:String,value:String){
        Log.e("$before$tag",value)
    }
}