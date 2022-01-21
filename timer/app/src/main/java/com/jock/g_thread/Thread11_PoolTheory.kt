package com.jock.g_thread

/**
 * Description: 线程池源码分析
 * Author: lxc
 * Date: 2022/1/19 21:57
 */
object Thread11_PoolTheory {

    val COUNT_BITS = Integer.SIZE - 3

    val CAPAITY = (1 shl COUNT_BITS) - 1

    val RUNNING = -1 shl COUNT_BITS
    val SHUTDOWN = 0 shl COUNT_BITS
    val STOP = 1 shl COUNT_BITS
    val TIDYING = 2 shl COUNT_BITS
    val TERMINATED = 3 shl COUNT_BITS

    @JvmStatic
    fun main(args: Array<String>) {
        CAPAITY.printBinaryString()
        println("----------")
        RUNNING.printBinaryString()
        SHUTDOWN.printBinaryString()
        STOP.printBinaryString()
        TIDYING.printBinaryString()
        TERMINATED.printBinaryString()
        println("----------")
        Int.MIN_VALUE.printBinaryString()
        // 取线程数量
        val workCount = RUNNING.and(CAPAITY).printBinaryString()
        val status = RUNNING.and(CAPAITY.inv()).printBinaryString()
        (3 shr 1).printBinaryString()
    }

    private fun Int.printBinaryString() {
        println(Integer.toBinaryString(this))
    }
}