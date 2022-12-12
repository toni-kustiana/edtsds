package id.co.edtslib.edtsds.list.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder

class MenuListView<T>: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val _adapter = MenuAdapter<T>()
    var delegate: MenuDelegate<T>? = null
    var data = listOf<T>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            _adapter.list = value.toMutableList()
            _adapter.notifyDataSetChanged()
        }

    init {
        _adapter.delegate = object : BaseRecyclerViewAdapterDelegate<T> {
            override fun onClick(t: T, position: Int, holder: BaseViewHolder<T>?) {
                delegate?.onSelected(t)
            }

            override fun onDraw(t: T, position: Int) {
            }
        }

        layoutManager = LinearLayoutManager(context)
        adapter = _adapter
    }
}