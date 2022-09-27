package id.co.edtslib.edtsds.list.banner

import com.bumptech.glide.Glide
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterBannerBinding

class BannerHolder(private val binding: AdapterBannerBinding): BaseViewHolder<String>(binding) {
    override fun setData(
        list: MutableList<String>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<String>?
    ) {
        Glide.with(binding.root.context).load(list[position]).into(binding.imageView)
    }
}