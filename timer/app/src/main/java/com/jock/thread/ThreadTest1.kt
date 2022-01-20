package com.jock.thread

import android.os.Process


/**
 * Description: 常规线程使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object ThreadTest1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val thread1 = Thread(Runnable {
            println("Thread1 run")
        })
        val thread2 = MyThread()
        // java 1-10
        thread1.priority = 10
        thread1.start()
        // android -20 - 19
        thread2.start()
    }

    class MyThread:Thread(){
        override fun run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT)
        }
    }
}