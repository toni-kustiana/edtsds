package id.co.edtslib.edtsds.list.checkboxlist.multiple

import android.content.Context
import android.util.AttributeSet
import id.co.edtslib.edtsds.list.checkboxlist.single.CheckBoxListView

class MultipleCheckBoxListView<T>: CheckBoxListView<T> {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun createAdapter() = MultipleCheckBoxAdapter<T>()
}