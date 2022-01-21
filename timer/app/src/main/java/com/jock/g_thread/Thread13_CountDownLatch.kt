package com.jock.g_thread

import java.util.*
import java.util.concurrent.*


/**
 * Description: CountDownLatch 使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread13_CountDownLatch {

    @JvmStatic
    fun main(args: Array<String>) {
      val downLatch = CountDownLatch(5)
        for(i in 1..5){
            Thread {
                try {
                    Thread.sleep(Random().nextInt(5000).toLong())
                    println("Thread-${i}:准备好了")
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
                downLatch.countDown()
            }.start()
        }
        downLatch.await()
        println("所有人准备好了，准备发车~~")
    }
}