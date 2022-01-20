package com.jock.library.task

import android.app.Application
import com.aice.appstartfaster.task.AppStartTask
import org.devio.hi.library.util.ActivityManager

/**
 * Description: ActivityManager 初始化
 * Author: lxc
 * Date: 2022/1/18 22:21
 */
class ActivityManageTask(private val application: Application): AppStartTask() {

    override fun run() {
       ActivityManager.instance.init(application)
    }

    override fun isRunOnMainThread(): Boolean {
        return true
    }
}