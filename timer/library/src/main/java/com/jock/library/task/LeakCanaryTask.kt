package com.jock.library.task

import android.app.Application
import com.aice.appstartfaster.task.AppStartTask

/**
 * Created by lxc on 1/9/22 4:26 PM.
 * Desc: 所有初始化的实例
 */



class LeakCanaryTask(private val application: Application) : AppStartTask() {
    override fun run() {
//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            return
//        }
//        LeakCanary.install(application)
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }
}