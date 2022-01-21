package com.jock.h_network

import android.os.Build
import androidx.annotation.RequiresApi
import org.devio.hi.library.restful.annotation.Filed
import org.devio.hi.library.restful.annotation.GET
import org.devio.hi.library.restful.annotation.POST
import org.devio.hi.library.restful.annotation.Path
import java.util.concurrent.Callable
import kotlin.jvm.JvmStatic

/**
 * Description: HiRestful之方法解析器
 * Author: lxc
 * Date: 2022/1/20 21:02
 */
object Network1_Test {
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun main(args: Array<String>) {
//        method1()
//        method2()
//        method3()
    }

    // 获取注解中的值
    private fun method3() {
        val clazz = AccountApi::class.java
        clazz.methods.forEach {
            it.parameterAnnotations.forEach {
                it.forEach {
                    if(it is Filed){
                        println("Filed:${it.value}")
                    }
                }
            }
        }
    }

    // 判断是否为基本类型
    private fun method2() {
        val value = 1
        val value2 = "1"
        println(isPrimitive(value))
        println(isPrimitive(value2))
    }

    /**
     * 判断是否为基本类型
     */
    private fun isPrimitive(value: Any): Boolean {
        try {
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return false
    }

    // 判断参数注解数是否正确
    private fun method1() {
        val clazz = AccountApi::class.java
        breaking@ for (method in clazz.methods) {
            println("---------")
            var isEqual = false
            if (method.parameterAnnotations.isEmpty()) {
                isEqual = true
            } else {
                for (array in method.parameterAnnotations) {
                    if (array.isEmpty() || array.size != 1) {
                        isEqual = false
                        println("method:${method.name} $isEqual 方法数跟注解数")
                        continue@breaking
                    }
                }
                isEqual = true
            }
            println("method:${method.name} $isEqual 方法数跟注解数")
        }
    }
}

interface AccountApi {

    @POST("user/login")
    fun login(
        @Filed("userName") userName: String,
        @Path("imoocId") @Filed("password") password: String
    ): Callable<String>


    @POST("user/registration")
    fun register(
        @Filed("userName") userName: String,
        @Filed("password") password: String,
//        @Filed("imoocId")
        imoocId: String,
        @Filed("orderId") orderId: String
    ): Callable<String>


    @GET("user/profile")
    fun profile(@Filed("userName") userName: String): Callable<String>


    @GET("notice")
    fun notice(): Callable<String>
}