package com.lxc.base.aop.aspectj

import android.util.Log
import android.view.View
import com.lxc.base.aop.epic.classSimpleName
import com.lxc.base.aop.epic.methodTag
import com.lxc.base.kutil.LogUtil
import com.lxc.base.performance.LifeEvent
import com.lxc.base.performance.PerformanceHandle
import com.lxc.base.thread.LifeStamp
import com.lxc.base.thread.Performance
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import java.lang.Throwable

/**
 * Created by lxc on 1/9/22 10:58 PM.
 * Desc: 点击超时检测
 */
@Aspect
class ViewAspect {

    private fun View.clickTag():String {
        return "${this.classSimpleName()}:${LifeEvent.VIEW_CLICK}"
    }

    @Around("execution(* android.view.View.OnClickListener.onClick(*))")
    @Throws(kotlin.Throwable::class)
    fun onAround(joinPoint: ProceedingJoinPoint) {
        val t = joinPoint.args[0] as? View ?: return
        Performance.startLifecycle(LifeStamp(t.clickTag()))
        joinPoint.proceed()
        val stamp =LifeStamp(t.clickTag())
        stamp.timeOut = {
            PerformanceHandle.clickTimeOut(t)
        }
        Performance.stopLifecycle(stamp,500)
    }
}