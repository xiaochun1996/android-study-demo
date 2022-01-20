package com.jock.library.executor

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import com.jock.library.library.log.LogUtil
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import androidx.annotation.IntRange

/**
 * Description: 线程池调度
 * Author: lxc
 * Date: 2022/1/19 14:54
 */
object JExecutor {

    private const val TAG: String = "Executor"
    private var isPaused: Boolean = false
    private var jExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock = ReentrantLock()
    private var pauseCondition: Condition = lock.newCondition()
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        // 暂停锁

        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount*2+19
        val blockingQueue :PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong(0)
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "executor-${seq.getAndIncrement()}"
            thread
        }

        jExecutor = object:ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,unit,blockingQueue as BlockingQueue<Runnable>,threadFactory){
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if(isPaused){
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务,线程创建数量,正在运行的数量
                LogUtil.e(TAG, "已执行完的任务的优先级是：" + (r as PriorityRunnable).priority)
            }
        }
    }

    fun execute(@IntRange(from = 0, to =10) priority: Int = 0, runnable: Runnable) {
        jExecutor.execute(PriorityRunnable(priority, runnable))
    }

    fun execute(@IntRange(from = 0, to =10) priority: Int = 0,runnable: Callable<*>) {
        jExecutor.execute(PriorityRunnable(priority, runnable))
    }

    abstract class Callable<T>:Runnable {
        override fun run() {
            mainHandler.post { onPrepare() }
            val t: T = onBackground()
            //移除所有消息.防止需要执行onCompleted了，onPrepare还没被执行，那就不需要执行了
            mainHandler.removeCallbacksAndMessages(null)
            mainHandler.post { onCompleted(t) }
        }

        // 请求完成
        abstract fun onCompleted(t: T)

        // 后台请求
        abstract fun onBackground(): T

        // 预处理
        open fun onPrepare(){

        }
    }

    /**
     * Runnable 包装，支持优先级
     */
    class PriorityRunnable(val priority: Int,private val runnable: Runnable) : Runnable,Comparable<PriorityRunnable>{
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }
    }

    fun pause() {
        lock.lock()
        try {
            if (isPaused) return
            isPaused = true
        } finally {
            lock.unlock()
        }
        LogUtil.e(TAG, "Executor is paused")
    }

    fun resume() {
        lock.lock()
        try {
            if (!isPaused) return
            isPaused = false
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        LogUtil.e(TAG, "Executor is resumed")
    }
}