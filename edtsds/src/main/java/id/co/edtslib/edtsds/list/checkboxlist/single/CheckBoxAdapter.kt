package id.co.edtslib.edtsds.list.checkboxlist.single

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterCheckboxBinding
import id.co.edtslib.edtsds.list.checkboxlist.CheckBoxListDelegate
import id.co.edtslib.edtsds.list.checkboxlist.DataSelected

open class CheckBoxAdapter<T>: BaseRecyclerViewAdapter<AdapterCheckboxBinding, T>() {
    init {
        delegate = object : BaseRecyclerViewAdapterDelegate<T> {
            override fun onClick(t: T, position: Int, holder: BaseViewHolder<T>?) {
                select(position)
            }

            override fun onDraw(t: T, position: Int) {
            }
        }
    }
    var menuDelegate: CheckBoxListDelegate<T>? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterCheckboxBinding
        get() = AdapterCheckboxBinding::inflate

    override fun createHolder() = CheckBoxHolder<T>(binding)

    open fun select(position: Int) {
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