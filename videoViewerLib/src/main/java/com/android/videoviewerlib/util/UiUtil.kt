package com.android.videoviewerlib.util

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import com.android.videoviewerlib.R

object UiUtil {
    val TAG = "UiUtil"

    fun setNoActionBar() {

    }

    fun setActivityTheme(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "设置主题")
            with(activity.window) {
                //沉浸式状态栏
//                statusBarColor = activity.resources.getColor(R.color.white)
//                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                //
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            }

        }
    }

}