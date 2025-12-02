package id.co.edtslib.edtsds.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.myapplication.databinding.AdapterStepperBinding

class StepperAdapter: BaseRecyclerViewAdapter<AdapterStepperBinding, Int>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterStepperBinding
        get() = AdapterStepperBinding::inflate

    override fun createHolder() = StepperHolder(binding)
}