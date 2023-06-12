package id.co.edtslib.edtsds.chips.sliding

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.databinding.AdapterChipBinding

class ChipAdapter<T>(private val textColor: Int,
                     private val textPadding: Float,
                     private val chipBackgroundColor: Int,
                     private val strokeColor: Int,
                     private val textStyle: Int,
                     private val iconPaddingStart: Float,
                     private val iconPaddingEnd: Float,
                     private val iconSize: Float):
    BaseRecyclerViewAdapter<AdapterChipBinding, T>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterChipBinding
        get() = AdapterChipBinding::inflate

    override fun createHolder() = ChipHolder<T>(binding,
        textColor,
        textPadding,
        chipBackgroundColor,
        strokeColor,
        textStyle,
        iconPaddingStart,
        iconPaddingEnd,
        iconSize)

}