package com.jock.g_thread

import java.util.*
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock


/**
 * Description: ReentrantLock Condition
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread9_ReentrantLockCondition {

    @JvmStatic
    fun main(args: Array<String>) {
        val task = LockTask()
        // 不断检查是否有砖，一边生产一边消费
        Thread {
            while (true){
                task.work1()
            }
        }.start()
        Thread {
            while (true){
                task.work2()
            }
        }.start()
        for(i in 1..10){
            task.boss()
        }
    }

    class LockTask {
        // 0 认为无砖
        private var flag: Int = 0
        private var worker1Condition: Condition
        private var worker2Condition: Condition

        private val lock = ReentrantLock(true)

        init {
            worker1Condition = lock.newCondition()
            worker2Condition = lock.newCondition()
        }

        fun boss() {
            try {
                lock.lock()
                flag = Random().nextInt(100)
                if (flag % 2 == 0) {
                    worker2Condition.signal()
                    println("生产出来了砖${flag}，唤醒工人 2 去搬")
                } else {
                    worker1Condition.signal()
                    println("生产出来了砖${flag}，唤醒工人 1 去搬")
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } finally {
                lock.unlock()
            }
        }

        fun work1() {
            try {
                lock.lock()
                if (flag == 0 || flag % 2 == 0) {
                    println("worker1 无砖可搬，休息会")
                    worker1Condition.await()
                }
                println("worker1 搬到的砖是：${flag}")
                flag = 0
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                lock.unlock()
            }
        }

        fun work2() {
            try {
                lock.lock()
                if (flag == 0 || flag % 2 != 0) {
                    println("worker2 无砖可搬，休息会")
                    worker2Condition.await()
                }
                println("worker2 搬到的砖是：${flag}")
                flag = 0
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                lock.unlock()
            }
        }
    }
}