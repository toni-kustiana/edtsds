package id.co.edtslib.edtsds.list.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.databinding.AdapterMenuBinding

class MenuAdapter<T>: BaseRecyclerViewAdapter<AdapterMenuBinding, T>() {
    var showIcon = true

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterMenuBinding
        get() = AdapterMenuBinding::inflate

    override fun createHolder() = MenuHolder<T>(binding, showIcon)
}