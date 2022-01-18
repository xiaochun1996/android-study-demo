package com.lxc.base.task

import android.app.Application
import com.aice.appstartfaster.task.AppStartTask
import com.github.anrwatchdog.ANRWatchDog
import com.github.anrwatchdog.ANRWatchDog.ANRListener
import com.lxc.base.kutil.LogUtil
import com.lxc.base.performance.PerformanceHandle


/**
 * Created by lxc on 1/9/22 4:26 PM.
 * Desc: ANR 监听
 */
class ANRListenerTask(private val application: Application) : AppStartTask() {

    var duration = 4L

    val silentListener = ANRListener { error ->
        LogUtil.e("$error") }

    override fun run() {
        ANRWatchDog(2000)
            .setANRListener {
                LogUtil.e(
                    "Intercepted ANR "
                )
                PerformanceHandle.anrHandler(it)
            }
            .setANRInterceptor {
                val ret: Long = this.duration * 1000 - duration
                if (ret > 0)
                    LogUtil.e(
                        "Intercepted ANR that is too short ($duration ms), postponing for $ret ms."
                    )
                return@setANRInterceptor ret
            }
            .start()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }
}