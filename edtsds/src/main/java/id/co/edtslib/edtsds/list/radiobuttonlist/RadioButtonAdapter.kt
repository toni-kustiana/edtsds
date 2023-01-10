package id.co.edtslib.edtsds.list.radiobuttonlist

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterRadioButtonBinding
import id.co.edtslib.edtsds.list.checkmenu.RadioButtonListDelegate

class RadioButtonAdapter<T>: BaseRecyclerViewAdapter<AdapterRadioButtonBinding, T>() {
    init {
        delegate = object : BaseRecyclerViewAdapterDelegate<T> {
            override fun onClick(t: T, position: Int, holder: BaseViewHolder<T>?) {
                select(position)
            }

            override fun onDraw(t: T, position: Int) {
            }
        }
    }
    var menuDelegate: RadioButtonListDelegate<T>? = null
    var gravity = Gravity.START

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterRadioButtonBinding
        get() = AdapterRadioButtonBinding::inflate

    override fun createHolder() = RadioButtonHolder<T>(binding, this)

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