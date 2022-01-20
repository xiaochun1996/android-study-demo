package com.jock.library.library.log.format

/**
 * Created by lxc on 1/13/22 9:53 PM.
 * Desc: 线程格式化
 */
class ThreadFormatter:LogFormatter<Thread> {

    override fun format(data: Thread): String {
        return "Thread:" + data.name
    }

}