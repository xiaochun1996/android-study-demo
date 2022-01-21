package com.jock.f_route.config

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PathReplaceService

/**
 * Description: 全局 url 处理
 * Author: lxc
 * Date: 2022/1/18 21:37
 */
@Suppress("unused")
@Route(path = "/service/path_replace")
class PathReplaceServiceImpl:PathReplaceService {

    // 仅调用一次
    override fun init(context: Context?) {
        
    }

    override fun forString(path: String): String {
        return  path
    }

    override fun forUri(uri: Uri): Uri {
        return  uri
    }
}