package com.jock.g_thread

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.library.executor.JExecutor
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView
import com.jock.ui.toast.toast

/**
 * Description: 线程池测试
 * Author: lxc
 * Date: 2022/1/19 09:26
 */
class Thread12_ActivityJExcutor : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = createContentView()

        val callBack = object : JExecutor.Callable<String>() {
            override fun onPrepare() {
                println("准备运行")
                toast("准备运行")
            }

            override fun onCompleted(t: String) {
                println("运行完成")
                toast(t)
            }

            override fun onBackground(): String {
                Thread.sleep(1000)
                return "我是后台请求的数据"
            }
        }

        linearLayout.addButton("CallBack") {
            JExecutor.execute(5,callBack)
        }
        linearLayout.addButton("Runnable") {
            JExecutor.execute(5,Runnable {
                runOnUiThread {
                    toast("我是 Runnable 运行")
                }
                println("Runnable 运行完成")
            })
        }
        linearLayout.addButton("批量任务") {
            for (i in 1..100){
                JExecutor.execute(5,Runnable {
                    println("${Thread.currentThread().name}已执行")
                    Thread.sleep(2000)
                })
            }
        }
        linearLayout.addButton("暂停任务"){
            JExecutor.pause()
        }
        linearLayout.addButton("继续任务"){
            JExecutor.resume()
        }
    }
}