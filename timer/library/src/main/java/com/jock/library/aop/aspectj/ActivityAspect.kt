package com.jock.library.aop.aspectj

import com.jock.library.aop.epic.classSimpleName
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance.startLifecycle
import com.jock.library.thread.Performance.stopLifecycle
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * Created by lxc on 1/8/22 5:51 PM.
 * Desc: Activity 切点
 */
//@Aspect
//class ActivityAspect {
//
//    @Around("execution(* android.app.Activity.setContentView(int))")
//    @Throws(Throwable::class)
//    fun onAround(joinPoint: ProceedingJoinPoint) {
//        val tag = "${joinPoint.target.classSimpleName()}.setContentView()"
//        startLifecycle(LifeStamp(tag))
//        joinPoint.proceed()
//        stopLifecycle(LifeStamp(tag))
//    }
//}