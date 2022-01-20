package com.jock.route.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jock.main.R
import com.jock.route.config.RouteFlag

/**
 * Description: 商品信息页面，需要登录权限
 * Author: lxc
 * Date: 2022/1/18 21:46
 */
@Route(path = "/goods/list", extras = RouteFlag.FLAG_LOGIN)
class InformActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}