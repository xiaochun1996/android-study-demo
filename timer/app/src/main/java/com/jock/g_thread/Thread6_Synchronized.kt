package com.jock.g_thread

import java.lang.Exception


/**
 * Description: Synchronized 的使用
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread6_Synchronized {


    @JvmStatic
    fun main(args: Array<String>) {
        testSafe()
    }

    private val tickets = ArrayList<String>()

    private fun testSafe(count:Int = 5){
        for (i in 1..count) {
            tickets.add("票_${i}")
        }
        sllTickets(count)
    }

    private fun sllTickets(count: Int) {
        val consumers = Consumers()
        for (i in 1..count) {
            Thread {
                Consumers.printThreadName()
            }.start()
        }
    }

    class Consumers {

        companion object {
            @Synchronized
            fun printThreadName(){
                val name = Thread.currentThread().name
                println("买票人：${name}准备好了...")
                try {
                    Thread.sleep(1000)
                } catch(e:Exception){
                    e.printStackTrace()
                }
                println("买票人：${name}买到的票是...，${tickets.removeAt(0)}")
            }
        }
    }
}