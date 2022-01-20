package com.jock.thread

import java.util.concurrent.locks.ReentrantReadWriteLock


/**
 * Description: ReentrantReadWriteLockClass 读写锁，读写互斥、谢谢互斥、读读并发
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object ThreadTest10 {


    @JvmStatic
    fun main(args: Array<String>) {
        val task = ReentrantReadWriteLockClass()
        Thread { task.write() }.start()
        Thread { task.read() }.start()
        Thread { task.read() }.start()
        Thread { task.write() }.start()
        Thread { task.read() }.start()
        Thread { task.read() }.start()
        Thread { task.write() }.start()
    }


    class ReentrantReadWriteLockClass {
        private val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()
        private val readLock: ReentrantReadWriteLock.ReadLock = lock.readLock()
        private val writeLock: ReentrantReadWriteLock.WriteLock = lock.writeLock()

        fun read() {
            val name = Thread.currentThread().name
            try {
                readLock.lock()
                println("线程${name}正在读取数据...")
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                readLock.unlock()
                println("线程${name}释放了读锁...")
            }
        }

        fun write() {
            val name = Thread.currentThread().name
            try {
                writeLock.lock()
                println("线程${name}正在写入数据...")
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                writeLock.unlock()
                println("线程${name}释放了写锁...")
            }
        }
    }
}