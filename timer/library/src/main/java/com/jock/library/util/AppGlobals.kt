package com.jock.library.util

import android.app.Application

/**
 * Description:全局变量
 * Author: lxc
 * Date: 2022/1/21 08:33
 */
object AppGlobals {

    private var application: Application?=null

    fun get():Application?{
        if(application ==null){
           try {
               application = Class.forName("android.app.ActivityThread")
                   .getMethod("currentApplication")
                   .invoke(null) as Application
           } catch (e:Exception) {
               e.printStackTrace()
           }
        }
        return application
    }
}