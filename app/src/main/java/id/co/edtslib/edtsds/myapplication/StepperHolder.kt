package id.co.edtslib.edtsds.myapplication

import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.myapplication.databinding.AdapterStepperBinding

class StepperHolder(private val binding: AdapterStepperBinding): BaseViewHolder<Int>(binding) {
    override fun setData(
        list: MutableList<Int>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<Int>?
    ) {
        binding.stepperView2.canEdit = true
    }
}