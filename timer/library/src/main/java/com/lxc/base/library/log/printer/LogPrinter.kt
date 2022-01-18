package com.lxc.base.library.log.printer

import com.lxc.base.library.log.LogConfig


interface LogPrinter {

    fun print(config: LogConfig, level:Int, tag:String, printString:String)
}
