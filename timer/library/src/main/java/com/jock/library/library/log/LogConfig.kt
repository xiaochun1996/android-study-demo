package com.jock.library.library.log

/**
 * 日志参数配置
 */
class LogConfig {

    var includeThread: Boolean =true

    var globalTag: String = "jock"

    var enable = false

    var stackTraceDepth = 5

    var MAX_LINE = 512

}
