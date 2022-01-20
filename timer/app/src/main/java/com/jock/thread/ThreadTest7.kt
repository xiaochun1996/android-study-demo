package com.jock.thread

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


/**
 * Description: 原子类、volatile、int 的使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object ThreadTest7 {



    @JvmStatic
    fun main(args: Array<String>) {
        val task = Task()
        val thread = Thread {
            for (x in 1..10000){
                task.addAtomic()
                task.addNormal()
                task.addVolatile()
            }
        }
        val thread2 = Thread {
            for (x in 1..10000){
                task.addAtomic()
                task.addNormal()
                task.addVolatile()
            }
        }
        thread.start()
        thread2.start()

        thread.join()
        thread2.join()

        println("Task——AtomicInteger:${task.integer} intNormal:${task.intNormal} volatile:${task.volatile}")
    }

    class Task{
        val integer = AtomicInteger(1)
        var intNormal = 0
        @Volatile
        var volatile = 0

        fun addAtomic(){
            integer.getAndIncrement()
        }

        fun addNormal(){
            intNormal++
        }

        fun addVolatile(){
            volatile++
        }
    }
}