package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import id.co.edtslib.edtsds.list.SlidingItemView
import id.co.edtslib.edtsds.myapplication.databinding.AdapterStepperBinding

@SuppressLint("NotifyDataSetChanged")
class SlidingStepperView: SlidingItemView<AdapterStepperBinding, Int> {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun isEqual(a: Int, b: Int) = false

    override fun createAdapter() = StepperAdapter()

}