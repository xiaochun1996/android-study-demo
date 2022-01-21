package com.jock.g_thread

import kotlinx.coroutines.*

/**
 * Description: 协程简单使用
 * Author: lxc
 * Date: 2022/1/19 15:32
 */
object Coroutines1_USED {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        GlobalScope.launch(Dispatchers.Main) {

        }
//        serial()
        parallel()
    }

    private suspend fun parallel() {
        val value1 = request1()
        val deferred2 = GlobalScope.async { request2(value1) }
        val deferred3 = GlobalScope.async { request3(value1) }
        val deferredList = listOf(deferred2,deferred3)
        deferredList.awaitAll()
    }

    private suspend fun serial() {
        val value1 = request1()
        val value2 = request2(value1)
        val value3 = request3(value2)
        println(value3)
    }

    suspend fun request1(): String {
        delay(200)
        println("协程 1:${Thread.currentThread().name} 已执行")
        return "1"
    }


    suspend fun request2(str: String): String {
        delay(200)
        println("协程 2:${Thread.currentThread().name} 已执行")
        return str
    }

    suspend fun request3(str: String): String {
        delay(200)
        println("协程 3:${Thread.currentThread().name} 已执行")
        return str
    }
}