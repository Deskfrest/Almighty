package com.android.almighty.data.model

data class ResourceModel(
    //文件名
    var name: String = "",
    //绝对路径
    var absolutePath : String = "",
    //父文件夹路径
    var parentPath : String = "",
    //扩展名
    var extension : String = "",
    //最后修改日期
    var lastModifiedDate : String = "",
    //是否是文件（文件/文件夹）
    var isFile : Boolean = false
)