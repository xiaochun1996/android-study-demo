package com.jock.ui.toast

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * Description: Toast 工具类
 * Author: lxc
 * Date: 2022/1/19 00:17
 */
fun Activity.toast(content:String){
    toast(this,content)
}

fun toast(context: Context, content:String){
    Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
}