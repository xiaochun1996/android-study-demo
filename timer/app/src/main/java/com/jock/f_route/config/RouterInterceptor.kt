package com.jock.f_route.config

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * 业务的拦截器，判断目标页是否具备预先定义好的属性
 * @see RouteFlag
 */
@Interceptor(name = "biz_interceptor", priority = 9)
open class RouterInterceptor : IInterceptor {
    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context;
    }

    /**
     * note:该方法运行在arouter的线程池中
     */
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val flag = postcard.extra

        if ((flag and (RouteFlag.FLAG_LOGIN) != 0)) {
            // todo:此处补充登录判断逻辑，若未登录则跳登录页面
            callback.onInterrupt(RuntimeException("need login"))
            loginIntercept()
        } else {
            callback.onContinue(postcard)
        }
    }

    private fun loginIntercept() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            Router.startActivity(null, destination = Router.Destination.ACCOUNT_LOGIN)
        }
    }
}