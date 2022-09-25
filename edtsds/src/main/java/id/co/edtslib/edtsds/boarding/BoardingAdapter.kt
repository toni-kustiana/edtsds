package id.co.edtslib.edtsds.boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.databinding.AdapterBoardingItemBinding

class BoardingAdapter: BaseRecyclerViewAdapter<AdapterBoardingItemBinding, BoardingData>() {
    var height = 0f
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterBoardingItemBinding
        get() = AdapterBoardingItemBinding::inflate

    override fun createHolder() = BoardingHolder(binding, height)
}