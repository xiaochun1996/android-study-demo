package com.lxc.base.library.log

import com.lxc.base.library.log.format.LogFormatter
import com.lxc.base.library.log.format.StackTraceFormatter
import com.lxc.base.library.log.format.StringFormatter
import com.lxc.base.library.log.printer.ConsolePrinter
import com.lxc.base.library.log.printer.LogPrinter

class LogManager private constructor(builder: Builder) {


    var printers: ArrayList<LogPrinter> = arrayListOf()
    var formats: ArrayList<LogFormatter<*>> = arrayListOf()

    var config: LogConfig = LogConfig()

    init {
        this.printers = builder.printers
        this.formats = builder.formats
        this.config = builder.config
    }


    class Builder {

        var printers: ArrayList<LogPrinter> = arrayListOf()

        var formats: ArrayList<LogFormatter<*>> = arrayListOf()

        var config: LogConfig = LogConfig()


        fun setConfig(logConfig: LogConfig): Builder {
            this.config = logConfig
            return this
        }

        fun addFormatter(logFormatter: LogFormatter<*>):Builder{
            formats.forEach {
                // 若已经添加过，则返回
                if(it.javaClass == logFormatter.javaClass){
                    return this
                }
            }
            formats.add(logFormatter)
            return this
        }

        fun addPrinter(logPrinter:LogPrinter): Builder {
            printers.forEach {
                // 若已经添加过，则返回
                if(it.javaClass == logPrinter.javaClass){
                    return this
                }
            }
            printers.add(logPrinter)
            return this
        }

        fun build(): LogManager {
            addFormatter(StringFormatter())
            addFormatter(StackTraceFormatter())
            addPrinter(ConsolePrinter())
            val logManager = LogManager(this)
            LogUtil.logManger(logManager)
            return logManager
        }
    }
}
