package com.lxc.base.aop.aspectj

import com.lxc.base.aop.epic.classSimpleName
import com.lxc.base.thread.LifeStamp
import com.lxc.base.thread.Performance.startLifecycle
import com.lxc.base.thread.Performance.stopLifecycle
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * Created by lxc on 1/8/22 5:51 PM.
 * Desc: Activity 切点
 */
@Aspect
class ActivityAspect {

    @Around("execution(* android.app.Activity.setContentView(int))")
    @Throws(Throwable::class)
    fun onAround(joinPoint: ProceedingJoinPoint) {
        val tag = "${joinPoint.target.classSimpleName()}.setContentView()"
        startLifecycle(LifeStamp(tag))
        joinPoint.proceed()
        stopLifecycle(LifeStamp(tag))
    }
}