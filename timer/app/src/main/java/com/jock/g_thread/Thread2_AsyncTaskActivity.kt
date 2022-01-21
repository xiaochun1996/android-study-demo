package com.jock.g_thread

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jock.a_main.R
import kotlinx.android.synthetic.main.activity_thread_test.*

/**
 * Description: AsyncTask Demo
 * Author: lxc
 * Date: 2022/1/18 23:27
 */
class Thread2_AsyncTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_test)

        // 串行
        bt_1.setOnClickListener {
            for (x in 1 .. 100){
                val myAsyncTask = MyAsyncTask()
                myAsyncTask.execute(x.toString())
            }
        }

        // 并行
        bt_2.setOnClickListener {
            for (x in 1 .. 100){
                AsyncTask.THREAD_POOL_EXECUTOR.execute {
                    println("AsyncTask onPreExecute $x")
                }
            }
        }
    }

    class MyAsyncTask:AsyncTask<String,Integer,String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            println("AsyncTask onPreExecute")
        }

        override fun doInBackground(vararg params: String?): String {
            println("AsyncTask doInBackground ${params[0]}")
            return ""
        }

        override fun onProgressUpdate(vararg values: Integer) {
            super.onProgressUpdate(*values)
            println("AsyncTask onProgressUpdate${values[0]}")
        }
    }
}