package com.jock.library.ui

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat

/**
 * Description: 测试使用 UI
 * Author: lxc
 * Date: 2022/1/19 00:05
 */
object TestUtil {

    fun ViewGroup.addButton(text:String, click:()->Unit) {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val button = Button(this.context)
        button.setOnClickListener {
            click.invoke()
        }
        button.text = text
        button.layoutParams = params
        this.addView(button)
    }

    fun Activity.createContentView():LinearLayoutCompat{
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        val linearLayout = LinearLayoutCompat(this)
        linearLayout.orientation = LinearLayoutCompat.VERTICAL
        linearLayout.gravity = Gravity.CENTER
        linearLayout.layoutParams = params
        this.setContentView(linearLayout)
        return linearLayout
    }
}