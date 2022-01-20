package com.jock.library.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.jock.library.aop.epic.classSimpleName
import com.jock.library.kutil.LogUtil
import com.jock.library.thread.LifeStamp
import com.jock.library.thread.Performance
import com.jock.library.ui.AsyncLayoutInflaterPlus2

/**
 * Created by lxc on 1/10/22 1:42 PM.
 * Desc: 基类 Activity
 */
abstract class BaseActivity:AppCompatActivity() {

    private val setContentViewTag = this.classSimpleName()+".setContentView()"

    private val factory2 = object : LayoutInflater.Factory2 {
        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            val startTime = System.currentTimeMillis()
            val view = delegate.createView(parent, name, context, attrs)
            val cost = System.currentTimeMillis() - startTime
            try {
                LogUtil.d("加载控件2：${view.classSimpleName()} ${view.context.classSimpleName()} 耗时：${cost}")
            } catch (e:Exception){

            }
            LogUtil.d("加载控件：" + name + "耗时：" + cost)
            return view
        }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            return null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater,factory2)
        super.onCreate(savedInstanceState)
        // 主要是解析的时间，所以从这里开始统计
        Performance.startLifecycle(LifeStamp(setContentViewTag))
        AsyncLayoutInflaterPlus2(this, factory2).inflate(layoutId(),null
        ) { view, _, _ ->
            setContentView(view)
            Performance.stopLifecycle(LifeStamp(setContentViewTag))
            initView()
        }
    }

    abstract fun initView()

    abstract fun layoutId():Int
}