package com.jock.g_thread

import java.util.*
import java.util.concurrent.*


/**
 * Description: Semaphore 使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread14_Semaphore {

    @JvmStatic
    fun main(args: Array<String>) {
      val semaphore = Semaphore(5)
        for(i in 1..10){
            Thread {
                try {
                    val count = Random().nextInt(4)+1
                    semaphore.acquire(count)
                    Thread.sleep(Random().nextInt(1000).toLong())
                    println("Thread-${i}:获取了${count}个许可证，正在执行~~")
                    // 获取几个要还几个
                    semaphore.release(count)
                    println("Thread-${i}:归还了${count}个许可证")
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }.start()
        }
        println("所有执行完毕")
    }
}