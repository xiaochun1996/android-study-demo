package com.lxc.base.task

import com.aice.appstartfaster.task.AppStartTask
import com.lxc.base.aop.epic.*

/**
 * eqic 初始化
 */
class EqicTask : AppStartTask() {

    override fun run() {
        ImageHook.hook()
        ViewHook.hook()
        ViewClickHook.hook()
        ActivityLifecycleHook.hook()
        ThreadHook.hook()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    /**
     * 为了第一个 Activity 能统计上
     */
    override fun needWait(): Boolean {
        return true
    }
}