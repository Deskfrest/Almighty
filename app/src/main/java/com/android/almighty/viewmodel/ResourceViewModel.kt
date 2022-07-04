package com.android.almighty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.almighty.data.model.ResourceModel
import java.io.File

class ResourceViewModel : ViewModel() {
    private val _resourceList = MutableLiveData<List<ResourceModel>>()
    val resourceList = _resourceList

    fun getResourceList(folderPath : String){
        val fileList = arrayListOf<ResourceModel>()
        val file = File(folderPath)
        val tempList = file.listFiles()
        tempList?.let { list->
            for(index in list.indices){
                val tempFile = list[index]
                fileList.add(ResourceModel(
                    name = tempFile.name,
                    path = tempFile.absolutePath,
                    parentPath = tempFile.parent ?: "",
                    isFile = tempFile.isFile)
                )
            }
        }
        _resourceList.postValue(fileList)
    }
}