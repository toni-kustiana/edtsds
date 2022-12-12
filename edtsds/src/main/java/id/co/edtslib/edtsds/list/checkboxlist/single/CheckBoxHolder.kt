package id.co.edtslib.edtsds.list.checkboxlist.single

import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterCheckboxBinding
import id.co.edtslib.edtsds.list.checkboxlist.DataSelected

class CheckBoxHolder<T>(private val binding: AdapterCheckboxBinding):
    BaseViewHolder<T>(binding) {
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        binding.textView.text = list[position].toString()

        if (list[position] is DataSelected) {
            val dataSelected = list[position] as DataSelected
            binding.root.isActivated = dataSelected.selected
        }
    }
}