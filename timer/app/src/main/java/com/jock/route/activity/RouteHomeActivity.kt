package com.jock.route.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jock.main.R
import com.jock.route.config.Router
import kotlinx.android.synthetic.main.activity_route_home.*

/**
 * Description: 登录页面
 * Author: lxc
 * Date: 2022/1/18 21:46
 */
@Route(path="/account/login")
class RouteHomeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_home)

        bt_inform.setOnClickListener {
            Router.startActivity(this,null,Router.Destination.INFORMATION)
        }

        bt_error.setOnClickListener {
            Router.startActivity(this,null,Router.Destination.ERROR)
        }
    }
}