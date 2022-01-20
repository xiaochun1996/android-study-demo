package com.jock.library.aop.epic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.jock.library.kutil.LogUtil
import com.jock.library.performance.LifeEvent
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook

/**
 * Created by lxc on 1/9/22 1:31 PM.
 * Desc: Activity 生命周期 Hook
 */
object ActivityLifecycleHook {

    var mPreviousActivity:String =""

    // 是否开始记录
    var isStartActivityRecord = false

    fun hook() {
        DexposedBridge.hookAllConstructors(Activity::class.java, object : XC_MethodHook() {

            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
            }
        })
        val activityMethodHook = ActivityMethodHook()
        DexposedBridge.findAndHookMethod(
            Activity::class.java, "onCreate", Bundle::class.java,
            activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onStart", activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onResume", activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onPause", activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onStop", activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onDestroy", activityMethodHook)
        DexposedBridge.findAndHookMethod(Activity::class.java, "onWindowFocusChanged",Boolean::class.java, activityMethodHook)
        DexposedBridge.findAndHookMethod(
            Activity::class.java, "startActivityForResult",
            Intent::class.java, Int::class.java, Bundle::class.java,
            activityMethodHook
        )
    }

    class ActivityMethodHook : XC_MethodHook() {

        // 是否已经创建
        var isStartPageRecord = false
        
        private fun Activity.startPageTag(): String {
            return "${this.classSimpleName()}:${LifeEvent.ACTIVITY_START_PAGE}"
        }

        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            super.beforeHookedMethod(param)
            val t = param.thisObject as? Activity ?: return
            if (param.method.toString().contains("onCreate")) {
                if(!isStartPageRecord){
                    Performance.startLifecycle(LifeStamp(t.startPageTag()))
                    isStartPageRecord = true
                }
            }
            // 如果是跳转页面
            if (param.method.toString().contains("startActivityForResult")) {
                isStartActivityRecord = false
                mPreviousActivity = t.classSimpleName()
                Performance.startLifecycle(LifeStamp("jump_page"))
            }
            Performance.startLifecycle(LifeStamp(t.methodTag(param)))
            Log.i("jock", "activity:${t.methodTag(param)}")
        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)
            val t = param.thisObject as? Activity ?: return
            // 防止在 onResume 之前调用跳转逻辑
            if (param.method.toString().contains("onWindowFocusChanged")) {
                if (isStartActivityRecord) {
                    val stamp = LifeStamp("jump_page")
                    stamp.timeOut = {
                        LogUtil.e("From $mPreviousActivity to ${t.classSimpleName()}")
                    }
                    Performance.stopLifecycle(stamp,10)
                    isStartActivityRecord = false
                }

                if(isStartPageRecord){
                    Performance.stopLifecycle(LifeStamp(t.startPageTag()))
                    isStartPageRecord = false
                }
            }

            Performance.stopLifecycle(LifeStamp(t.methodTag(param)))
            Log.i("jock", "activity:${t.methodTag(param)}")
        }
    }
}