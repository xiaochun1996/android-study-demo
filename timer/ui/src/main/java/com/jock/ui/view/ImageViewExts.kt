package com.jock.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.security.MessageDigest


/**
 * Description: Glide 扩展
 * Author: lxc
 * Date: 2022/1/21 08:51
 */

/**
 * 普通图片
 */
fun ImageView.loadUrl(url:String){
    Glide.with(context).load(url).into(this)
}

/**
 * 圆形图片
 */
fun ImageView.loadCircle(url:String){
    Glide.with(context).load(url).transform(CircleCrop()).into(this)
}

/**
 * 圆角图片
 */
fun ImageView.loadCorner(url:String,corner:Int){
    // glide 的 图片裁剪 和 imageview scaleType 有冲突 centerCrop .
    Glide.with(context).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)
}

/**
 * 带描边的图片
 */
fun ImageView.loadCircleBorder(
    url: String,
    borderWidth: Float = 0f,
    borderColor: Int = Color.WHITE
) {
    Glide.with(this).load(url).transform(CircleBorderTransform(borderWidth, borderColor)).into(this)
}

class CircleBorderTransform(private val borderWidth: Float, borderColor: Int) : CircleCrop() {
    private var borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val VERSION = 1
    private val ID = "com.bumptech.glide.load.resource.bitmap.CircleBorderCrop.$VERSION"
    private val ID_BYTES = ID.toByteArray(CHARSET)

    init {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)

        val halfWidth = outWidth / 2.toFloat()
        val halfHeight = outHeight / 2.toFloat()


        canvas.drawCircle(
            halfWidth,
            halfHeight,
            halfWidth.coerceAtMost(halfHeight) - borderWidth / 2,
            borderPaint
        )

        canvas.setBitmap(null)

        return transform
    }

    /**
     * 防止复用问题，设置缓存 key
     */
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}