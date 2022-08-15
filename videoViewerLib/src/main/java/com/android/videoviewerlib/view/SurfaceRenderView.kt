package com.android.videoviewerlib.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import com.android.videoviewerlib.util.MeasureHelper

/**
 * 继承SurfaceView的子类
 */
class SurfaceRenderView :
    SurfaceView, IRenderView {
    private val TAG = "SurfaceRenderView"

    private lateinit var mMeasureHelper: MeasureHelper

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    //缩放比例计算所需要的一些参数
    companion object {
        const val AR_ASPECT_FIT_PARENT = 0
        const val AR_ASPECT_FILL_PARENT = 1
        const val AR_ASPECT_WRAP_CONTENT = 2
        const val AR_MATCH_PARENT = 3
        const val AR_16_9_FIT_PARENT = 4
        const val AR_4_3_FIT_PARENT = 5
    }

    //实现接口方法，获取到视频文件长宽分辨率
    override fun setVideoSize(width: Int, height: Int) {
        if (width > 0 && height > 0) {
            Log.i(TAG, "setVideoSize: $width $height")
            //获取到视频文件长宽分辨率，存储以备计算缩放比例
            mMeasureHelper.setVideoSize(width, height)
            holder.setFixedSize(width, height)
            requestLayout()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //传入当前SurfaceView的长宽比例,开始计算视频渲染所需要的真实缩放比
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec)
        //将实际需要的尺寸设置给SurfaceView
        setMeasuredDimension(mMeasureHelper.measuredWidth, mMeasureHelper.measuredHeight)
    }

    fun initView() {
        mMeasureHelper = MeasureHelper(this)
    }
}