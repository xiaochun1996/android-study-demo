package com.jock.f_route.config

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PretreatmentService

/**
 * Description: 全局预处理
 * Author: lxc
 * Date: 2022/1/18 21:37
 */
@Suppress("unused")
@Route(path = "/service/pretreatment")
class PretreatmentServiceImpl:PretreatmentService {

    // 仅调用一次
    override fun init(context: Context?) {
        
    }

    override fun onPretreatment(context: Context?, postcard: Postcard?): Boolean {
        // 若需要自己处理，打断流程返回 false
        return true
    }

}