package id.co.edtslib.edtsds.list.checkboxlist.single

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.edtsds.list.checkboxlist.CheckBoxListDelegate

open class CheckBoxListView<T>: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var lAdapter: CheckBoxAdapter<T>

    open fun createAdapter() = CheckBoxAdapter<T>()

    var delegate: CheckBoxListDelegate<T>? = null
        set(value) {
            field = value
            lAdapter.menuDelegate = delegate
        }
    var data = listOf<T>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            lAdapter.list = value.toMutableList()
            lAdapter.notifyDataSetChanged()
        }
    var selectedIndex = 0
        set(value) {
            field = value
            lAdapter.select(value)
        }

    init {
        layoutManager = LinearLayoutManager(context)
        lAdapter = createAdapter()
        adapter = lAdapter
    }
}