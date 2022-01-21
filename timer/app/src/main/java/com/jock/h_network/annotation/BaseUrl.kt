package com.jock.h_network.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Description:
 * Author: lxc
 * Date: 2022/1/20 21:09
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class BaseUrl(val value: String)