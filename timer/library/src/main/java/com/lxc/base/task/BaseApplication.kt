package com.lxc.base.task

import android.app.Application
import android.content.Context
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.lxc.base.performance.LifeEvent
import com.lxc.base.thread.LifeStamp
import com.lxc.base.thread.Performance

/**
 * Created by lxc on 1/8/22 10:56 PM.
 * Desc:
 */
class BaseApplication:Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Performance.init()
        Performance.startLifecycle(LifeStamp(LifeEvent.LAUNCHED_EVENT))
    }

    override fun onCreate() {
        super.onCreate()
        AppStartTaskDispatcher.create()
            .setShowLog(true)
            .setAllTaskWaitTimeOut(3000)
            .addAppStartTask(EqicTask())
            .addAppStartTask(LogTask())
//            .addAppStartTask(LeakCanaryTask(this))
            .addAppStartTask(FpsTask())
//            .addAppStartTask(StrictModeTask())
            .addAppStartTask(ANRListenerTask(this))
//            .addAppStartTask(UITask(this))
            .start()
            .await()
    }

}