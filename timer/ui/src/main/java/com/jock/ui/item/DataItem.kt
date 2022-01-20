package com.jock.ui.item

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class  DataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA?) {
    val TAG: String = "HiDataItem";
    var baseAdapter: BaseAdapter? = null
    var mData: DATA? = null

    init {
        this.mData = data
    }

    /**
     * 绑定数据
     */
    abstract fun onBindData(holder: VH, position: Int)

    /**
     * 返回该item的布局资源id
     */
    open fun getItemLayoutRes(): Int {
        return -1
    }

    /**
     *返回该item的视图view
     */
    open fun getItemView(parent: ViewGroup): View? {
        return null
    }

    fun setAdapter(adapter: BaseAdapter) {
        this.baseAdapter = adapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        if (baseAdapter != null) baseAdapter!!.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem() {
        if (baseAdapter != null) baseAdapter!!.removeItem(this)
    }

    /**
     * 该item在列表上占几列,代表的宽度是沾满屏幕
     */
    open fun getSpanSize(): Int {
        return 0
    }

    /**
     * 该item被滑进屏幕
     */
    open fun onViewAttachedToWindow(holder: VH) {

    }

    /**
     * 该item被滑出屏幕
     */
    open fun onViewDetachedFromWindow(holder: VH) {

    }

}