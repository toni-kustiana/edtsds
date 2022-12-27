package id.co.edtslib.edtsds.list.radiobuttonlist

import android.view.Gravity
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.AdapterRadioButtonBinding

class RadioButtonHolder<T>(private val binding: AdapterRadioButtonBinding,
                           private val adapter: RadioButtonAdapter<T>):
    BaseViewHolder<T>(binding) {
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        binding.textView.text = list[position].toString()
        if (adapter.gravity == Gravity.END) {
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_radio, 0)
        }
        else {
            binding.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio, 0, 0, 0)
        }

        if (list[position] is DataSelected) {
            val dataSelected = list[position] as DataSelected
            binding.root.isActivated = dataSelected.selected
        }
    }
}