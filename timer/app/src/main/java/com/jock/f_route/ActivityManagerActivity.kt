package com.jock.f_route

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.devio.hi.library.util.ActivityManager

/**
 * Description:
 * Author: lxc
 * Date: 2022/1/18 22:31
 */
class ActivityManagerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.instance.addFrontBackCallback(object : ActivityManager.FrontBackCallback{
            override fun onChanged(front: Boolean) {
                val status= if(front) "前" else "后"
                Toast.makeText(this@ActivityManagerActivity, "应用处于${status}台", Toast.LENGTH_SHORT).show()
            }
        })
    }
}