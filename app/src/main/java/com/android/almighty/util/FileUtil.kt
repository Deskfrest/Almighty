package com.android.almighty.util

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

object FileUtil {
    fun getPath(context : Context) : String{
        val state = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        var dir : File? = null
        dir = if(state){
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                context.getExternalFilesDir(null)
            }else{
                Environment.getExternalStorageDirectory()
            }
        }else{
            Environment.getRootDirectory()
        }
        return dir.toString()
    }
}