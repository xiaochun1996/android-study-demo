package com.jock.library.aop.aspectj

import android.view.View
import com.jock.library.aop.epic.classSimpleName
import com.jock.library.performance.LifeEvent
import com.jock.library.performance.PerformanceHandle
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * Created by lxc on 1/9/22 10:58 PM.
 * Desc: 点击超时检测
 */
//@Aspect
//class ViewAspect {
//
//    private fun View.clickTag():String {
//        return "${this.classSimpleName()}:${LifeEvent.VIEW_CLICK}"
//    }
//
//    @Around("execution(* android.view.View.OnClickListener.onClick(*))")
//    @Throws(kotlin.Throwable::class)
//    fun onAround(joinPoint: ProceedingJoinPoint) {
//        val t = joinPoint.args[0] as? View ?: return
//        Performance.startLifecycle(LifeStamp(t.clickTag()))
//        joinPoint.proceed()
//        val stamp =LifeStamp(t.clickTag())
//        stamp.timeOut = {
//            PerformanceHandle.clickTimeOut(t)
//        }
//        Performance.stopLifecycle(stamp,500)
//    }
//}