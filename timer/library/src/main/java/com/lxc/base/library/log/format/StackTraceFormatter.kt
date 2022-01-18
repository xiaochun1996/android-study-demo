package com.lxc.base.library.log.format

import java.lang.StringBuilder
import java.text.Format

/**
 * Created by lxc on 1/13/22 10:30 PM.
 * Desc: 栈信息打印
 */
class StackTraceFormatter:LogFormatter<Array<StackTraceElement>> {

    override fun format(data: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (datum in data) {
            sb.append(datum).append("\n")
        }
        return sb.toString()
    }
}