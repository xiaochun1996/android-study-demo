package com.jock.library.task

import android.app.Application
import com.aice.appstartfaster.task.AppStartTask
import com.alibaba.android.arouter.launcher.ARouter
import com.jock.library.BuildConfig

/**
 * Description: ARouter 初始化
 * Author: lxc
 * Date: 2022/1/18 22:21
 */
class ARouterTask(private val application: Application): AppStartTask() {

    override fun run() {
        if(BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    // 因为初始化后阻塞，所以不需要主线程等待
    override fun isRunOnMainThread(): Boolean {
        return false
    }
}