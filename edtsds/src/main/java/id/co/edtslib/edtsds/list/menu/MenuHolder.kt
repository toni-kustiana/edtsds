package id.co.edtslib.edtsds.list.menu

import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterMenuBinding

class MenuHolder<T>(private val binding: AdapterMenuBinding,
                    private val showIcon: Boolean = true):
    BaseViewHolder<T>(binding) {
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        binding.textView.text = list[position].toString()
        if (! showIcon) {
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }
}