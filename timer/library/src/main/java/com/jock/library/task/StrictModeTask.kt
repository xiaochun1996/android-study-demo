package com.jock.library.task

import android.os.StrictMode
import com.aice.appstartfaster.task.AppStartTask
import com.jock.library.BuildConfig


/**
 * Created by lxc on 1/9/22 4:26 PM.
 * Desc: 所有初始化的实例
 */


class StrictModeTask : AppStartTask() {
    override fun run() {
        // 1、设置Debug标志位，仅仅在线下环境才使用StrictMode
        if (BuildConfig.DEBUG) {
            // 2、设置线程策略
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    //API等级11，使用StrictMode.noteSlowCode
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    // or .detectAll() for all detectable problems
                    .detectNetwork()
                    //在Logcat 中打印违规异常信息
                    .penaltyLog()
                    .penaltyDialog() //也可以直接跳出警报dialog
//              .penaltyDeath() //或者直接崩溃
                    .build()
            )
            // 3、设置虚拟机策略
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    // 给NewsItem对象的实例数量限制为1
//                    .setClassInstanceLimit(NewsItem.class, 1)
                    //API等级11
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            );
        }
    }

    override fun isRunOnMainThread(): Boolean {
        return true
    }
}