package id.co.edtslib.edtsds.list.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R

class MenuListView<T>: RecyclerView {
    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private val _adapter = MenuAdapter<T>()
    var delegate: MenuDelegate<T>? = null
    var data = listOf<T>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            _adapter.list = value.toMutableList()
            _adapter.notifyDataSetChanged()
        }

    var showIcon = true
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            _adapter.showIcon = value
            _adapter.notifyDataSetChanged()
        }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.MenuListView,
                0, 0
            )

            showIcon = a.getBoolean(R.styleable.MenuListView_showIcon, false)

            a.recycle()
        }

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