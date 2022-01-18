package com.lxc.base.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.lxc.base.kutil.LogUtil
import com.lxc.base.performance.LifeEvent
import com.lxc.base.performance.PerformanceHandle

/**
 * Created by lxc on 1/8/22 10:14 PM.
 * Desc:
 */

object Performance {

    internal const val LIFECYCLE_START = 1

    internal const val LIFECYCLE_STOP = 2

    private var handler:Handler ?= null


    fun init() {
        val performanceThread = PerformanceThread("performanceThread")
        performanceThread.start()
        handler = performanceThread.handler
    }

    fun startLifecycle(stamp: LifeStamp) {
        val message = Message()
        message.what = LIFECYCLE_START
        message.obj = stamp
        handler?.sendMessage(message)
    }

    fun stopLifecycle(stamp: LifeStamp,limit:Int =100) {
        val message = Message()
        message.what = LIFECYCLE_STOP
        message.arg1 = limit
        message.obj = stamp
        handler?.sendMessage(message)
    }

}

data class LifeStamp(
    val key: String, val value: Long = System.currentTimeMillis(),
    var timeOut: (() -> Unit?)? = null)

private class PerformanceThread(name: String) : HandlerThread(name) {

    override fun run() {
        super.run()
        // loop 停止之前这里执行不到
        println("jock 性能线程退出")
    }

    var array = LinkedHashMap<String, Long>()

     val handler by lazy {
         object : Handler(looper) {
             override fun handleMessage(msg: Message) {
                 super.handleMessage(msg)
                 LogUtil.i(msg.toString())
                 when (msg.what) {
                     Performance.LIFECYCLE_START -> {
                         val stamp = msg.obj as? LifeStamp
                         stamp?.let {
                             val value = array[it.key]
                             if (value != null) {
                                 LogUtil.e(LifeEvent.TAG,"数组中已存在${it.key}，值为${it.value}")
                                 return
                             }
                             array[it.key] = it.value
                         }
                     }
                     Performance.LIFECYCLE_STOP -> {
                         val stamp = msg.obj as? LifeStamp
                         stamp?.let {
                             val start = array[it.key]
                             if (start == null) {
                                 LogUtil.e(LifeEvent.TAG,"数组中不存在${it.key}")
                                 return
                             }
                             val time = stamp.value - start
                             if(time>msg.arg1){
                                 PerformanceHandle.methodTimeOut(it.key,time)
                                 stamp.timeOut?.invoke()
                             }else {
                                 LogUtil.i(LifeEvent.TAG,"${it.key}:${time}")
                             }
                             array.remove(it.key)
                         }
                     }
                 }
             }
         }
     }

}