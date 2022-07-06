package com.android.mdviewerlib

import java.io.File
import java.util.*

object TxtUtil {
    fun readTxt(path : String) : String{
        var str = ""
        try {
            val file = File(path)
            val scanner = Scanner(file, "UTF-8" )
            str = scanner.useDelimiter("\\A").next()
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return  str
    }
}