package id.co.edtslib.edtsds.list.checkboxlist.multiple

import id.co.edtslib.edtsds.list.checkboxlist.DataSelected
import id.co.edtslib.edtsds.list.checkboxlist.single.CheckBoxAdapter

class MultipleCheckBoxAdapter<T>: CheckBoxAdapter<T>() {
    override fun select(position: Int) {
        if (list[position] is DataSelected) {
            val dataSelected = list[position] as DataSelected
            dataSelected.selected = ! dataSelected.selected
            notifyItemChanged(position)

            menuDelegate?.onSelected(getSelectedItems())
        }
    }

    private fun getSelectedItems(): List<T> {
        val result = mutableListOf<T>()
        list.forEach {
            if (it is DataSelected && it.selected) {
                result.add(it)
            }
        }

        return result
    }
}