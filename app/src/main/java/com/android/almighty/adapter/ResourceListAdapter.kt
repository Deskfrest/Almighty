package com.android.almighty.adapter

import androidx.databinding.DataBindingUtil
import com.android.almighty.R
import com.android.almighty.data.model.ResourceModel
import com.android.almighty.databinding.ItemResourceBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ResourceListAdapter() :
    BaseQuickAdapter<ResourceModel, BaseViewHolder>(R.layout.item_resource) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemResourceBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: ResourceModel) {
        DataBindingUtil.getBinding<ItemResourceBinding>(holder.itemView)?.let {binding ->
            binding.tvResourceName.text = item.name
            binding.tvLastModified.text = item.lastModifiedDate
            if(!item.isFile){
                binding.ivResourceType.setBackgroundResource(R.drawable.ic_folder)
            }else{
                when(item.extension){
                    "bmp" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_bmp)
                    "doc" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_doc)
                    "gif" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_gif)
                    "iso" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_iso)
                    "jpg","JPEG","jpeg","JPG" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_jpg)
                    "md" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_md)
                    "mp3" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_mp3)
                    "mp4" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_mp4)
                    "pdf" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_pdf)
                    "ppt" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_ppt)
                    "rar" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_rar)
                    "rmvb" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_rmvb)
                    "torrent" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_torrent)
                    "txt" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_txt)
                    "wav" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_wav)
                    "wma" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_wma)
                    "wps" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_wps)
                    "xls" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_xls)
                    "zip" -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_zip)
                    else -> binding.ivResourceType.setBackgroundResource(R.drawable.ic_unknown)
                }
            }
        }
    }

}