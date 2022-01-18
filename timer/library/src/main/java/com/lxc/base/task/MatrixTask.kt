package com.lxc.base.task

import android.app.Application
import com.aice.appstartfaster.task.AppStartTask


/**
 * Created by lxc on 1/11/22 3:49 PM.
 * Desc: 卡顿监控
 */
class MatrixTask(private val application: Application) :AppStartTask(){
    override fun run() {
//        val builder: Matrix.Builder = Matrix.Builder(application) // build matrix

//        builder.pluginListener(TestPluginListener(this)) // add general pluginListener
//
//        val dynamicConfig = DynamicConfigImplDemo() // dynamic config
//        // init plugin
//        val ioCanaryPlugin = IOCanaryPlugin(
//            IOConfig.Builder()
//                .dynamicConfig(dynamicConfig)
//                .build()
//        )
//        //add to matrix
//        builder.plugin(ioCanaryPlugin)
        //init matrix
//        Matrix.init(builder.build())
        // start plugin
//        ioCanaryPlugin.start()
    }

    override fun isRunOnMainThread(): Boolean {
        return true
    }


}