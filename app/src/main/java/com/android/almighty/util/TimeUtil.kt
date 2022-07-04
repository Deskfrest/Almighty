package com.android.almighty.util

import android.os.Environment
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun stampToDate(stamp : Long,dateFormat : String = "yyyy-MM-dd") : String{
        val state = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = stamp
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }
}