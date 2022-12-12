package id.co.edtslib.edtsds.list.checkmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterCheckMenuBinding

class CheckMenuAdapter<T>: BaseRecyclerViewAdapter<AdapterCheckMenuBinding, T>() {
    init {
        delegate = object : BaseRecyclerViewAdapterDelegate<T> {
            override fun onClick(t: T, position: Int, holder: BaseViewHolder<T>?) {
                select(position)
            }

            override fun onDraw(t: T, position: Int) {
            }
        }
    }
    var menuDelegate: CheckMenuDelegate<T>? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterCheckMenuBinding
        get() = AdapterCheckMenuBinding::inflate

    override fun createHolder() = CheckMenuHolder<T>(binding)

    fun select(position: Int) {
        var i = 0
        list.forEach {
            if (it is DataSelected) {
                val newSelected = i == position
                val dataSelected = it as DataSelected
                if (newSelected != dataSelected.selected) {
                    dataSelected.selected = newSelected
                    notifyItemChanged(i)
                }

                if (newSelected) {
                    menuDelegate?.onSelected(it)
                }
            }

            i++
        }
    }
}