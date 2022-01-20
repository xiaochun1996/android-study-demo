package com.jock.library.base

import android.app.Application
import android.content.Context
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.jock.library.performance.LifeEvent
import com.jock.library.task.*
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance

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
            .addAppStartTask(ActivityManageTask(this))
//            .addAppStartTask(StrictModeTask())
            .addAppStartTask(AnrListenerTask(this))
            .addAppStartTask(ARouterTask(this))
//            .addAppStartTask(UITask(this))
            .start()
            .await()
    }

}