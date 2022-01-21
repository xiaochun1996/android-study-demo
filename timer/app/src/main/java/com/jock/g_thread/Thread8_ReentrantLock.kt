package com.jock.g_thread

import java.lang.Exception
import java.util.concurrent.locks.ReentrantLock


/**
 * Description: ReentrantLock 使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread8_ReentrantLock {

    private val task = ReentrantLockTask()

    @JvmStatic
    fun main(args: Array<String>) {
        for (i in 1..10){
            Thread {
                task.buyTicker()
            }.start()
        }
    }

    class ReentrantLockTask {

        // 手动加锁释放锁，所以必须 finally 释放
        private val reentrantLock = ReentrantLock(true)

        fun buyTicker(){
            val name = Thread.currentThread().name
            try {
                reentrantLock.lock()
                println("${name}:准备好了")
                Thread.sleep(100)
                println("${name}:买好了")

                reentrantLock.lock()
                println("${name}:又准备好了")
                Thread.sleep(100)
                println("${name}:又买好了")

                reentrantLock.lock()
                println("${name}:又又准备好了")
                Thread.sleep(100)
                println("${name}:又又买好了")
            } catch (e:Exception){
                e.printStackTrace()
            }finally {
                reentrantLock.unlock()
                reentrantLock.unlock()
                reentrantLock.unlock()
            }
        }
    }
}