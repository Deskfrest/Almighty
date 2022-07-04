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
            binding.ivResourceType.setBackgroundResource(if(item.isFile) R.drawable.ic_file else R.drawable.ic_folder)
        }
    }

}