package com.jock.debugtool

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Debug(val name: String, val desc: String)