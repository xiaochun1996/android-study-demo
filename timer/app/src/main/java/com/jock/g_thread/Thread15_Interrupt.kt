package com.jock.g_thread

import java.lang.Thread.sleep


/**
 * Description: Semaphore 使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread15_Interrupt {

    @JvmStatic
    fun main(args: Array<String>) {
//        method1()
//        method2()
        method3()
    }

    private fun method3() {
        // 判断中断
        val thread3 = object : Thread() {

            override fun run() {
                super.run()
                while (true) {
                    if (!interrupted()) {
                        println("正在执行中")
                    } else {
                        println("线程被终止")
                        return
                    }
                }
            }
        }
        thread3.start()
        sleep(2000)
        thread3.interrupt()
    }

    private fun method2() {
        // boolean 标志位
        val thread2 = object : Thread() {

            var interrupt = false

            override fun run() {
                super.run()
                while (true) {
                    if (!interrupt) {
                        println("正在执行中")
                    } else {
                        println("线程被终止")
                        return
                    }
                }
            }
        }
        thread2.start()
        sleep(2000)
        thread2.interrupt = true
    }

    private fun method1() {
        // 捕获中断
        val thread = Thread {
            try {
                sleep(5000)
                // 捕获支持中断
            } catch (e: InterruptedException) {
                println("线程被终止")
            }
        }

        thread.start()
        thread.interrupt()
    }
}