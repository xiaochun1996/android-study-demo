package com.jock.route.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jock.main.R

/**
 * Description: 登录页面
 * Author: lxc
 * Date: 2022/1/18 21:46
 */
@Route(path="/account/login")
class LoginActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}