package com.lxc.base.performance

import android.util.Log
import android.view.View
import com.github.anrwatchdog.ANRError
import com.lxc.base.aop.epic.classSimpleName
import com.lxc.base.kutil.LogUtil

/**
 * Created by lxc on 1/9/22 6:21 PM.
 * Desc: 收集处理
 */
object PerformanceHandle {

    /**
     * 方法超时处理
     */
    fun methodTimeOut(key: String, time: Long) {
        LogUtil.e(LifeEvent.TAG, "${key}:${time}")
    }

    /**
     * 大图警告
     */
    fun largeBitmapWarn(
        view: View,
        viewWidth: Int,
        viewHeight: Int,
        bitmapWidth: Int,
        bitmapHeight: Int
    ){
        LogUtil.e("View:context:${view.context.classSimpleName()} class:${view.classSimpleName()} id:${view.id}")
        LogUtil.e("${view.javaClass.simpleName}: w:${viewWidth},h:${viewHeight} bitmap.w:${bitmapWidth},bitmap.h:${bitmapHeight} to large")
    }

    /**
     * 线程收敛
     */
    fun threadConvergence(t: Thread) {
        // 没有名字的线程打印出来
        if (t.name.isEmpty()){
            LogUtil.e(Log.getStackTraceString(Throwable()))
        }
    }

    /**
     * 线程卡顿异常
     */
    fun fpsAbnormal(fps: Double) {
        if(fps<50){
            LogUtil.e("当前帧率：${fps}")
        }
    }

    /**
     * 点击超时预警
     */
    fun clickTimeOut(t: View) {
        LogUtil.e("View:context:${t.context.classSimpleName()} class:${t.classSimpleName()} id:${t.id}")
        val stack = Log.getStackTraceString(Throwable())
        LogUtil.e(stack)
    }

    /**
     * ANR 处理
     */
    fun anrHandler(it: ANRError) {
        try {
            throw it
        } catch (e: ANRError){
            LogUtil.e("ANR:${e.printStackTrace()}")
            e.printStackTrace()
        }
//        LogUtil.e("ANR:${it}")
    }
}