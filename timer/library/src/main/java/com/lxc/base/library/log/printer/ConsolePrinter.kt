package com.lxc.base.library.log.printer

import android.R.attr
import android.util.Log
import com.lxc.base.library.log.LogConfig


/**
 * Created by lxc on 1/13/22 3:01 PM.
 * Desc: 控制台打印
 */
class ConsolePrinter : LogPrinter {

    override fun print(config: LogConfig, level: Int, tag: String, printString: String) {
        val MAX_LEN = config.MAX_LINE
        val len: Int = printString.length
        val countOfSub: Int = len / MAX_LEN
        if (countOfSub > 0) {
            val log = StringBuilder()
            var index = 0
            for (i in 0 until countOfSub) {
                log.append(printString.substring(index, index + MAX_LEN))
                index += MAX_LEN
            }
            if (index != len) {
                log.append(printString.substring(index, len))
            }
            Log.println(level, tag, log.toString())
        } else {
            Log.println(level, tag, printString)
        }
    }
}