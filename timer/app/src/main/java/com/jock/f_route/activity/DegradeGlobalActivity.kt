package com.jock.f_route.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jock.a_main.R
import com.jock.f_route.config.Router

/**
 * 全局统一错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ARouter.getInstance().inject(this)
        Router.inject(this)
        setContentView(R.layout.layout_global_degrade)
    }
}