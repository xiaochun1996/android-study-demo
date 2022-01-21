package com.jock.g_thread

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView

/**
 * Description: wait、join、yield、sleep 的使用
 * Author: lxc
 * Date: 2022/1/19 09:26
 */
class Thread4_WaitJoinYieldSleepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = createContentView()
        linearLayout.addButton("wait 调用") {
            var hasNotify = false
            val obj = Object()
            Thread {
               println("线程 1 执行之前")
               synchronized(obj) {
                   // 只有未 notify 的时候才执行 wait
                   if(!hasNotify){
                       obj.wait()
                   }
                   // 防止假死
//                   obj.wait(2000)
                   Thread.sleep(2000)
               }
               println("线程 1 执行之后")
           }.start()
            Thread {
                println("线程 2 执行之前")
                synchronized(obj){
                    Thread.sleep(2000)
                    obj.notify()
                    hasNotify = true
                }
                println("线程 2 执行之后")
            }.start()
        }
        linearLayout.addButton("join 调用") {
            // 串行执行
            val thread1 = Thread {
                println("线程 1 执行之前")
                Thread.sleep(2000)
                println("线程 1 执行之后")
            }
            thread1.start()
            // 主线程阻塞等待
            thread1.join()
            Thread {
                println("线程 2 执行之前")
                Thread.sleep(2000)
                println("线程 2 执行之后")
            }.start()
        }
        linearLayout.addButton("yield 调用") {
            val thread1 = Thread {
                println("线程 1 执行之前")
                // 暂停给其他线程执行机会
                Thread.yield()
                Thread.sleep(2000)
                println("线程 1 执行之后")
            }
            thread1.start()
            Thread {
                println("线程 2 执行之前")
                Thread.sleep(2000)
                println("线程 2 执行之后")
            }.start()
        }
        linearLayout.addButton("sleep 调用") {
            // 线程 1 先执行，然后线程 1 睡眠 5 秒，因为未释放锁
            // 线程 2 只能等待释放锁才能继续向下执行
            val obj = Object()
            val thread1 = Thread {
                println("线程 1 执行之前")
                synchronized(obj) {
                    // 暂停但持有锁
                    Thread.sleep(5000)
                }
                println("线程 1 执行之后")
            }
            thread1.start()
            Thread {
                Thread.sleep(2000)
                println("线程 2 执行之前")
                synchronized(obj) {
                    println("获取到锁--执行中")
                }
                println("线程 2 执行之后")
            }.start()
        }
    }
}