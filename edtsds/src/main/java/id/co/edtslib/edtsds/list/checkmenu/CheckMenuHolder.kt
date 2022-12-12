package id.co.edtslib.edtsds.list.checkmenu

import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.AdapterCheckMenuBinding

class CheckMenuHolder<T>(private val binding: AdapterCheckMenuBinding):
    BaseViewHolder<T>(binding) {
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        binding.textView.text = list[position].toString()

        if (list[position] is DataSelected) {
            val dataSelected = list[position] as DataSelected
            val resId = if (dataSelected.selected) R.drawable.ic_checked else 0
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0)
        }
    }
}