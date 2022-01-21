package com.jock.g_thread

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import java.util.function.UnaryOperator


/**
 * Description: AtomicReference、AtomicReferenceFieldUpdater 使用.
 *      修改原子属性
 * Author: lxc
 * Date: 2022/1/18 23:19
 */
object Thread16_AtomicReference {

    class AtomicReferenceValueHolder {
        val atomicValue = AtomicReference<String>("Hello Atomic")
    }

    class SimpleValueHolder {
        companion object {
            val valueUpdater = AtomicReferenceFieldUpdater.newUpdater(
                SimpleValueHolder::class.java,
                String::class.java,
                "value"
            )

        }

        @Volatile
        var value: String = "Hello Atomic"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @JvmStatic
    fun main(args: Array<String>) {
        method1()
        method2()

        method3()
    }

    // 修改原子引用
    private fun method3() {
        val reference: AtomicReference<Date> = AtomicReference()
        // 设置初始值
        val date1 = Date()
        reference.set(date1)
        // 需要的修改后的值
        val date2 = Date()
        reference.compareAndSet(date1, date2)
        println(reference.get())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun method2() {
        val holder = SimpleValueHolder()
        SimpleValueHolder.valueUpdater.compareAndSet(holder, "Hello", "World")
        val value =
            SimpleValueHolder.valueUpdater.getAndUpdate(holder, object : UnaryOperator<String> {
                override fun apply(t: String): String {
                    return "HelloWorld"
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun method1() {
        val holder = AtomicReferenceValueHolder()
        holder.atomicValue.compareAndSet("Hello", "World")
        val value = holder.atomicValue.getAndUpdate(object : UnaryOperator<String> {
            override fun apply(t: String): String {
                return "HelloWorld"
            }
        })
    }


}