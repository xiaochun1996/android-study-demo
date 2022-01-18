package com.lxc.base.task

import com.aice.appstartfaster.task.AppStartTask
import com.lxc.base.library.log.LogConfig
import com.lxc.base.library.log.LogManager
import com.lxc.base.library.log.format.ThreadFormatter
import com.lxc.base.library.log.printer.ConsolePrinter

/**
 * Created by lxc on 1/13/22 4:10 PM.
 * Desc: 日志初始化任务
 */
class LogTask : AppStartTask() {
    override fun run() {
        val logConfig = LogConfig().also {
            it.globalTag = "jock_new"
        }
        LogManager.Builder()
            .setConfig(logConfig)
            .addPrinter(ConsolePrinter())
            .addFormatter(ThreadFormatter())
            .build()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun needWait(): Boolean {
        return true
    }
}