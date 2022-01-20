package com.jock.library.task

import android.annotation.TargetApi
import android.os.Build
import android.view.Choreographer
import com.aice.appstartfaster.task.AppStartTask
import com.jock.library.performance.PerformanceHandle

/**
 * Created by lxc on 1/9/22 4:26 PM.
 * Desc: 所有初始化的实例
 */


class FpsTask : AppStartTask() {
    private var mStartFrameTime: Long = 0
    private var mFrameCount = 0

    /**
     * 单次计算FPS使用160毫秒
     */
    private val MONITOR_INTERVAL = 160L
    private val MONITOR_INTERVAL_NANOS = MONITOR_INTERVAL * 1000L * 1000L

    /**
     * 设置计算fps的单位时间间隔1000ms,即fps/s
     */
    private val MAX_INTERVAL = 1000L

    /**
     * 每个线程都有自己的 Choreographer
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun getFPS() {
        // 循环统计
        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (mStartFrameTime == 0L) {
                    mStartFrameTime = frameTimeNanos
                }
                val interval = frameTimeNanos - mStartFrameTime
                if (interval > MONITOR_INTERVAL_NANOS) {
                    val fps = (mFrameCount * 1000L * 1000L).toDouble() / interval * MAX_INTERVAL
                    PerformanceHandle.fpsAbnormal(fps)
                    mFrameCount = 0
                    mStartFrameTime = 0
                } else {
                    ++mFrameCount
                }
                Choreographer.getInstance().postFrameCallback(this)
            }
        })
    }

    override fun run() {
        getFPS()
    }

    override fun isRunOnMainThread(): Boolean {
        return true
    }
}