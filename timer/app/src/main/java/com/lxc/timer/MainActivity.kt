package com.lxc.timer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lxc.base.library.log.LogUtil
import com.lxc.base.performance.LifeEvent
import com.lxc.base.thread.LifeStamp
import com.lxc.base.thread.Performance
import com.zhangyue.we.x2c.ano.Xml


/**
 *
 * @author lxc
 * @date 1/8/22 7:56 PM
 * Desc:
 */
class MainActivity : AppCompatActivity() {

    var isRecord = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.tv_button)
        view.setOnClickListener {
//            Thread.sleep(6000)
            startActivity(Intent(this, GlideActivity::class.java))
//            LogUtil.e("123","2314","123")
        }
        println("onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart")
    }

    override fun onResume() {
        super.onResume()
        if(!isRecord){
            Performance.stopLifecycle(LifeStamp(LifeEvent.LAUNCHED_EVENT))
            isRecord = true
        }
        println("onResume")
    }
}