package com.jock.h_network

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.a_main.R
import com.jock.library.util.StatusBar
import com.jock.ui.view.loadCircle
import com.jock.ui.view.loadCircleBorder
import com.jock.ui.view.loadCorner
import com.jock.ui.view.loadUrl
import kotlinx.android.synthetic.main.activity_network_glide.*

/**
 * Description:Glide 图片加载
 * Author: lxc
 * Date: 2022/1/20 22:46
 */
 class Network8_GlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 沉浸式
        StatusBar.setStatusBar(this,false,Color.BLACK,true)
        val url = "https://img2.baidu.com/it/u=1207052543,1018931359&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
        setContentView(R.layout.activity_network_glide)
        im_one.loadUrl(url)
        im_two.loadCircle(url)
        im_three.loadCorner(url,40)
        im_four.loadCircleBorder(url,20F,Color.RED)
    }
 }