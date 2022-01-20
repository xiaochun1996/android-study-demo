package com.jock.thread

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantReadWriteLock


/**
 * Description: 线程池使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object ThreadTest11 {

    private val number = AtomicInteger(1)

    @JvmStatic
    fun main(args: Array<String>) {
        val blockingQueue = LinkedBlockingQueue<Runnable>(10)
        val threadFactory = ThreadFactory { r ->
            val thread = Thread(r)
            thread.name = "thread-${number}"
            number.getAndIncrement()
            thread
        }
        val handler = RejectedExecutionHandler { r, executor -> println("线程池执行错误") }
        val threadPool = ThreadPoolExecutor(2, 6,
            6000, TimeUnit.MILLISECONDS, blockingQueue,threadFactory,handler)

        for(i in 1..100){
            threadPool.execute {
                println("${Thread.currentThread().name}已执行")
            }
        }
    }
}