package com.lxc.base.library.log.format

import java.lang.reflect.Type

/**
 * Created by lxc on 1/13/22 8:53 PM.
 * Desc: 不同对象的解析
 */
interface LogFormatter<T> {

    /**
     * 根据输入数据，反推输出数据
     */
    fun format(data:T):String

}