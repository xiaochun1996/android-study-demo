package com.jock.library.library.log.printer

import com.jock.library.library.log.LogConfig


interface LogPrinter {

    fun print(config: LogConfig, level:Int, tag:String, printString:String)
}
