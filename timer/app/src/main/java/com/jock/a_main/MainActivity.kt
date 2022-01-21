package com.jock.a_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.g_thread.*
import com.jock.h_network.Network7_UIActivity
import com.jock.h_network.Network8_GlideActivity
import com.jock.i_demo.RestartAppActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 *
 * @author lxc
 * @date 1/8/22 7:56 PM
 * Desc:
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_button.setOnClickListener {
            startActivity(Intent(this, RestartAppActivity::class.java))
        }
    }
}