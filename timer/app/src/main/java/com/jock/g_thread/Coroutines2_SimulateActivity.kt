package com.jock.g_thread

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.Continuation

/**
 * Description: 线程间通信
 * Author: lxc
 * Date: 2022/1/19 09:26
 */
class Coroutines2_SimulateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = createContentView()
        linearLayout.addButton("手写协程回调") {
            val callback = Continuation<String>(Dispatchers.Main){ result ->
                println(result.getOrNull())
            }
            Coroutines2_Simulate.request1(callback)
        }
    }
}