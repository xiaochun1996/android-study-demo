package com.lxc.timer

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by lxc on 1/15/22 10:51 AM.
 * Desc:
 */
class GlideActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        val url = "https://img2.baidu.com/it/u=1207052543,1018931359&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
        val imageView = findViewById<ImageView>(R.id.iv_content)
        val imageView2 = findViewById<ImageView>(R.id.iv_content2)
        val btLoad = findViewById<View>(R.id.bt_load)
        val option = RequestOptions()
        option.override(2000)
        btLoad.setOnClickListener {
            Glide.with(this).load(R.drawable.ic_launcher_background).apply(option).skipMemoryCache(true).into(imageView2)
            Glide.with(this).load(url).apply(option).skipMemoryCache(true).into(imageView)
        }
    }

}