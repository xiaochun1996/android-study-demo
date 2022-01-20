package com.jock.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.thread.*
import com.jock.util.ActivityManagerActivity
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
            startActivity(Intent(this, CoroutinesDemoActivity2::class.java))
        }
    }
}