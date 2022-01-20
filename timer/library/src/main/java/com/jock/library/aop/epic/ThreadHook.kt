package com.jock.library.aop.epic

import com.jock.library.kutil.LogUtil
import com.jock.library.performance.PerformanceHandle
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook

/**
 * Created by lxc on 1/9/22 1:24 AM.
 * Desc:
 */
object ThreadHook {
    fun hook() {
        // hook 构造函数
        DexposedBridge.hookAllConstructors(Thread::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
            }
        })
        // hook 普通方法
        DexposedBridge.findAndHookMethod(Thread::class.java, "run", ThreadMethodHook())
    }

    class ThreadMethodHook : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            super.beforeHookedMethod(param)
            val t = param.thisObject as Thread
            PerformanceHandle.threadConvergence(t)
            LogUtil.i("thread-${t.name}: started..")
        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)
            val t = param.thisObject as Thread
            LogUtil.i("thread-${t.name}: exit..")
        }
    }
}