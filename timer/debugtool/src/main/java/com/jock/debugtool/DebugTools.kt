package com.jock.debugtool

import android.app.Application
import android.content.Intent
import android.os.Process
import com.jock.library.util.AppGlobals
import com.jock.library.util.SPUtil


/**
 * Description: 测试工具
 * Author: lxc
 * Date: 2022/1/21 12:45
 */
class DebugTools {

//    fun buildVersion():String {
//        // 构建版本 ： 1.0.1
//        return "构建版本:${BuildConfig.VERSION_CODE}.${BuildConfig.VERSION_CODE}"
//    }

    fun buildTime():String{
        //new date() 当前你在运行时拿到的时间，这个包，被打出来的时间
        return "构建时间：" + BuildConfig.BUILD_TIME
    }

    @Debug(name="一键开启 Https 降级",desc = "将继承 Http，可以使用抓包工具明文抓包")
    fun degrade2Http(){
        SPUtil.putBoolean("degrade_http",true)
        val context = AppGlobals.get()?.applicationContext?:return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        // 得到启动页的 intent，重新启动
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        //杀掉当前进程,并主动启动新的 启动页，以完成重启的动作
        Process.killProcess(Process.myPid())
    }
}