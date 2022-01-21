package com.jock.g_thread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView

/**
 * Description: 线程间通信
 * Author: lxc
 * Date: 2022/1/19 09:26
 */
class Thread5_CommunicationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = createContentView()
        linearLayout.addButton("向子线程发送消息") {
            val handlerThread = object : HandlerThread("子线程") {}
            handlerThread.start()
            val handler = Handler(handlerThread.looper) {
                println("线程名字：${Thread.currentThread().name}：")
                println("子线程接收到了消息 ${it.what}")
                true
            }
            for (x in 1..20) {
                handler.sendEmptyMessage(x)
            }
        }
        linearLayout.addButton("向主线程发送消息") {
            val handler = Handler(mainLooper) {
                println("线程名字：${Thread.currentThread().name}：")
                println("主线程接收到了消息 ${it.what}")
                true
            }
            for (x in 1..20) {
                handler.sendEmptyMessage(x)
            }
        }
        linearLayout.addButton("子线程间发送消息") {
            lateinit var handler1: Handler
            lateinit var handler2: Handler
            val handlerThread = object : HandlerThread("子线程") {}
            val handlerThread2 = object : HandlerThread("子线程2") {}
            handlerThread.start()
            handlerThread2.start()
            handler1 = Handler(handlerThread.looper) {
                println("线程名字：${Thread.currentThread().name}：")
                println("子线程接收到了消息 ${it.what}")
                handler2.sendEmptyMessage(it.what)
                true
            }
            handler2 = Handler(handlerThread2.looper) {
                println("线程名字：${Thread.currentThread().name}：")
                println("子线程2接收到了消息 ${it.what}")
                handler2.sendEmptyMessage(it.what)
                true
            }

            for (x in 1..20) {
                handler1.sendEmptyMessage(x)
            }
        }
    }
}