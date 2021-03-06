package com.jock.library.aop.epic

import android.util.Log
import android.view.View
import com.jock.library.kutil.LogUtil
import com.jock.library.performance.LifeEvent
import com.jock.library.performance.PerformanceHandle
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance
import de.robv.android.xposed.XC_MethodHook


/**
 * Created by lxc on 1/9/22 4:44 PM.
 * Desc: 按钮点击检测
 */
object ViewClickHook {

    fun hook(){
//        DexposedBridge.hookAllConstructors(View::class.java,object :XC_MethodHook(){
//            override fun afterHookedMethod(param: MethodHookParam?) {
//                super.afterHookedMethod(param)
//            }
//        })
//        DexposedBridge.findAndHookMethod(View::class.java,"performClick", ViewMethodHook())
    }

    class ViewMethodHook : XC_MethodHook() {

        private fun View.clickTag():String {
            return "${this.classSimpleName()}:${LifeEvent.VIEW_CLICK}"
        }

        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            super.beforeHookedMethod(param)
            val t = param.thisObject as? View ?: return
            Performance.startLifecycle(LifeStamp(t.clickTag()))
            LogUtil.e(t.context.classSimpleName())
            Log.i("jock", "view:${t.methodTag(param)}")
        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)
            val t = param.thisObject as? View ?: return
            val stamp =LifeStamp(t.clickTag())
            stamp.timeOut = {
                PerformanceHandle.clickTimeOut(t)
            }
            Performance.stopLifecycle(stamp)
            Log.i("jock", "view:${t.methodTag(param)}")
        }
    }
}