package com.lxc.base.aop.epic

import de.robv.android.xposed.XC_MethodHook

/**
 * Created by lxc on 1/9/22 10:12 PM.
 * Desc: 公共方法
 */

fun Any.classSimpleName(): String {
    return this.javaClass.simpleName
}

fun Any.methodTag(param: XC_MethodHook.MethodHookParam) = "${this.classSimpleName()}:${param.method.name}"
