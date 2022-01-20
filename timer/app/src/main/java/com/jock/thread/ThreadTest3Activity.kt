package com.jock.thread

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.main.R
import kotlinx.android.synthetic.main.activity_thread_pool.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Description: Java 中封装的四种线程池
 * Author: lxc
 * Date: 2022/1/18 23:27
 */
class ThreadTest3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_pool)
        // 缓存
        bt_1.setOnClickListener {
            val cachedPool = Executors.newCachedThreadPool()
            for (x in 1 .. 100){
                cachedPool.execute {
                    println("cachedPool onExecute $x")
                    Thread.sleep(100)
                }
            }
        }

        // 固定
        bt_2.setOnClickListener {
            val fixPool = Executors.newFixedThreadPool(2)
            for (x in 1 .. 100){
                fixPool.execute {
                    println("FixedPool onExecute $x")
                    Thread.sleep(100)
                }
            }
        }

        // 定时
        bt_3.setOnClickListener {
            val fixPool = Executors.newScheduledThreadPool(3)
            for (x in 1 .. 100){
                fixPool.schedule(Runnable {
                    println("ScheduledPool onExecute $x")
                    Thread.sleep(100)
                }, Random(5).nextLong(5000L),TimeUnit.MILLISECONDS)
            }
        }

        // 串行
        bt_4.setOnClickListener {
            val fixPool = Executors.newSingleThreadExecutor()
            for (x in 1 .. 100){
                fixPool.execute {
                    println("SinglePool onExecute $x")
                    Thread.sleep(100)
                }
            }
        }
    }
}