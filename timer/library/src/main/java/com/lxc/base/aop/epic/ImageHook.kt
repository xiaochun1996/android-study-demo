package com.lxc.base.aop.epic

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.ImageView
import com.lxc.base.kutil.LogUtil
import com.lxc.base.performance.PerformanceHandle
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook


/**
 * Created by lxc on 1/9/22 4:44 PM.
 * Desc: 图片检测
 */

object ViewHook {

    fun hook() {
        DexposedBridge.hookAllConstructors(View::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
            }
        })
        DexposedBridge.findAndHookMethod(
            View::class.java, "setBackgroundDrawable",
            Drawable::class.java, ImageMethodHook()
        )
    }

    class ImageMethodHook : XC_MethodHook() {
        override fun afterHookedMethod(param: MethodHookParam?) {
            super.afterHookedMethod(param)
            val imageView = param?.thisObject as? View
            imageView?.run {
                LogUtil.v("view: tag-${tag} id-${id}")
                val drawable = param.args[0] as Drawable
                LogUtil.v("view: $drawable.javaClass.simpleName}")
                checkBitmap(this, drawable)
            }
        }
    }
}

object ImageHook {

    fun hook() {
        DexposedBridge.hookAllConstructors(ImageView::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
            }
        })
        DexposedBridge.findAndHookMethod(
            ImageView::class.java, "setImageDrawable",
            Drawable::class.java, ImageMethodHook()
        )
    }

    class ImageMethodHook : XC_MethodHook() {
        override fun afterHookedMethod(param: MethodHookParam?) {
            super.afterHookedMethod(param)
            val imageView = param?.thisObject as? ImageView
            imageView?.run {
                LogUtil.i("imageView: tag-${tag} id-${id} drawable-${drawable}")
                val drawable = drawable
                drawable ?: return
                LogUtil.i("imageView: $drawable.javaClass.simpleName}")
                checkBitmap(this, drawable)
            }
        }
    }
}

internal fun checkBitmap(view: View, drawable: Drawable) {
    if (drawable is BitmapDrawable) {
        val bitmap = drawable.bitmap
        val width = view.width
        val height = view.height
        if (width > 0 && height > 0) {
            // 图标宽高都大于view带下的2倍以上，则警告
            if (bitmap.width >= width shl 1 && bitmap.height >= height shl 1) {
                PerformanceHandle.largeBitmapWarn(view,width, height, bitmap.width, bitmap.height)
            }
        } else {
            view.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val w = view.width
                    val h = view.height
                    if (w > 0 && h > 0) {
                        if (bitmap.width >= w shl 1
                            && bitmap.height >= h shl 1
                        ) {
                            PerformanceHandle.largeBitmapWarn(view,w, h, bitmap.width, bitmap.height)
                        }
                        view.viewTreeObserver.removeOnPreDrawListener(this)
                    }
                    return true
                }
            })
        }
    }
}