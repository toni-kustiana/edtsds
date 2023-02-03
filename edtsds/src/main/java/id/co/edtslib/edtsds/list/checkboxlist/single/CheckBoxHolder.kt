package id.co.edtslib.edtsds.list.checkboxlist.single

import android.view.Gravity
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.AdapterCheckboxBinding
import id.co.edtslib.edtsds.list.checkboxlist.DataSelected

class CheckBoxHolder<T>(private val binding: AdapterCheckboxBinding,
                        private val adapter: CheckBoxAdapter<T>):
    BaseViewHolder<T>(binding) {
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        binding.textView.text = list[position].toString()

        if (adapter.gravity == Gravity.END) {
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checkbox, 0)
        }
        else {
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checkbox, 0, 0, 0)
        }

        if (list[position] is DataSelected) {
            val dataSelected = list[position] as DataSelected
            binding.root.isActivated = dataSelected.selected
        }
    }
}