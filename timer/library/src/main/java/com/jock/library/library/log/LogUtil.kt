package com.jock.library.library.log

import android.annotation.SuppressLint
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType


/**
 * Created by lxc on 1/13/22 2:03 PM.
 * Desc: 日志打印方法类
 */
object LogUtil {

    private var logManager: LogManager? = null

    fun v(vararg contents: Any?) {
        log(LogType.V, *contents)
    }

    fun vt(tag: String, vararg contents: Any?) {
        log(LogType.V, tag, *contents)
    }

    fun d(vararg contents: Any?) {
        log(LogType.D, *contents)
    }

    fun dt(tag: String, vararg contents: Any?) {
        log(LogType.D, tag, *contents)
    }

    fun i(vararg contents: Any?) {
        log(LogType.I, *contents)
    }

    fun it(tag: String, vararg contents: Any?) {
        log(LogType.I, tag, *contents)
    }

    fun w(vararg contents: Any?) {
        log(LogType.W, *contents)
    }

    fun wt(tag: String, vararg contents: Any?) {
        log(LogType.W, tag, *contents)
    }

    fun e(vararg contents: Any?) {
        log(LogType.E, *contents)
    }

    fun et(tag: String, vararg contents: Any?) {
        log(LogType.E, tag, *contents)
    }

    fun a(vararg contents: Any?) {
        log(LogType.A, *contents)
    }

    fun at(tag: String, vararg contents: Any?) {
        log(LogType.A, tag, *contents)
    }

    private fun log(@LogType.TYPE type: Int, vararg contents: Any?){
        log(type,logManager?.config?.globalTag ?: "",*contents)
    }

    private fun log(
        @LogType.TYPE type: Int,
        tag: String,
        vararg contents: Any?
    ) {
        log(logManager?.config,type,tag,*contents)
    }

    private fun log(
        config: LogConfig?,
        @LogType.TYPE type: Int,
        tag: String,
        vararg contentArray: Any?
    ) {
        if(logManager==null){
            throw Exception("必须先用 LogManager 进行初始化")
        }
        val contentList = contentArray.toMutableList()
        config ?: return
        // 不打印则返回
        if (config.enable) {
            return
        }
        val sb = StringBuilder()

        if(config.stackTraceDepth>0){
            contentList.add(0,Throwable().stackTrace)
        }

        if (config.includeThread) {
            contentList.add(0,Thread.currentThread())
        }

        var body = parseBody(config, contentList)
        if (body.isNotBlank()) {
            body = body.replace("\\\"", "\"")
        }
        sb.append(body)
        logManager!!.printers.forEach {
            it.print(config, type, tag, sb.toString())
        }
    }

    /**
     * 解析对象
     */
    @SuppressLint("NewApi")
    private fun parseBody(config: LogConfig, contents: List<Any?>): String {
        val sb = StringBuilder().append("\n")
        contents.forEach out@{
            logManager!!.formats.forEach { formatter->
                val type = formatter.javaClass.genericInterfaces[0]
                if(type !is ParameterizedType || it==null) return@forEach
                val rawType = type.actualTypeArguments[0]
                if(rawType.typeName == it.javaClass.typeName){
                    // 无法直接调用，使用反射
                    val method = formatter.javaClass.getDeclaredMethod("format",it::class.java)
                    sb.append( method.invoke(formatter,it)).append("\n")
                    // 仅适配第一个
                    return@out
                }
            }
            sb.append(it.toString()).append(";")
        }
        if (sb.isNotBlank()) {
            sb.deleteCharAt(sb.lastIndex)
        }
        return sb.toString()
    }

    fun logManger(logManager: LogManager) {
        this.logManager = logManager
    }

}