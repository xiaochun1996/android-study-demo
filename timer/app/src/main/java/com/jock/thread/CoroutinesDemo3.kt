package com.jock.thread

import kotlinx.coroutines.*

/**
 * Description: 实现真正挂起函数
 * Author: lxc
 * Date: 2022/1/19 15:32
 */
object CoroutinesDemo3 {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        planA()
        planB()
        Unit
    }


    // 方案二：二者的区别就是是否能接收到取消事件
    private suspend fun planB(): String {
        return suspendCancellableCoroutine { coroutine ->
            Thread {
                coroutine.resumeWith(Result.success(parseAssetsFile()))
            }.start()
        }
    }

    // 方案一：变成了直接在主线程中调用了
    private suspend fun planA() {
        coroutineScope {
            val fileContent = this.async { parseAssetsFile() }.await()
            println(fileContent)
        }
    }

    private fun parseAssetsFile(): String {
        Thread.sleep(3000)
        println("当前线程：${Thread.currentThread().name}")
        return "文件内容读取成功..."
    }
}