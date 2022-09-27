package id.co.edtslib.edtsds.list.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.databinding.AdapterBannerBinding

class BannerAdapter: BaseRecyclerViewAdapter<AdapterBannerBinding, String>() {
    var height = 0

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterBannerBinding
        get() = AdapterBannerBinding::inflate

    override fun createHolder() = BannerHolder(binding, height)
}