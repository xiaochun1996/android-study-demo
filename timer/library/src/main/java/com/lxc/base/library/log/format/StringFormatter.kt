package com.lxc.base.library.log.format

import java.lang.reflect.Type

/**
 * Created by lxc on 1/13/22 9:30 PM.
 * Desc: 处理 String 的解析器
 */
class StringFormatter:LogFormatter<String> {

    override fun format(data: String): String {
       return "------------"
    }

}