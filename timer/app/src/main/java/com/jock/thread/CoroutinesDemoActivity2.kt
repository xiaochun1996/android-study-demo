package com.jock.thread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation

/**
 * Description: 线程间通信
 * Author: lxc
 * Date: 2022/1/19 09:26
 */
class CoroutinesDemoActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = createContentView()
        linearLayout.addButton("手写协程回调") {
            val callback = Continuation<String>(Dispatchers.Main){ result ->
                println(result.getOrNull())
            }
            CoroutinesDemo2.request1(callback)
        }
    }
}