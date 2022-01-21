package com.jock.i_demo

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.jock.a_main.BuildConfig
import com.jock.debugtool.DebugToolDialogFragment
import com.jock.library.ui.TestUtil.addButton
import com.jock.library.ui.TestUtil.createContentView
import com.jock.library.util.AppGlobals
import com.jock.ui.toast.toast
import java.lang.reflect.InvocationTargetException

/**
 * Description: 重新启动应用
 * Author: lxc
 * Date: 2022/1/21 12:57
 */
class RestartAppActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = createContentView()
        layout.addButton("重启应用"){
            val context = AppGlobals.get()?: return@addButton
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            // 得到启动页的 intent，重新启动
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            //杀掉当前进程,并主动启动新的 启动页，以完成重启的动作
            Process.killProcess(Process.myPid())
        }
    }

    // 监听音量键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if(BuildConfig.DEBUG){
                try {
                    toast("音量下键被点击")
//                    val aClass = Class.forName("com.jock.debugtool.DebugToolDialogFragment")
                    val aClass = DebugToolDialogFragment::class.java
                    val target = aClass.getConstructor().newInstance() as DialogFragment
                    target.show(supportFragmentManager, "debug_tool")
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
            // 拦截响应
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}