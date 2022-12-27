package id.co.edtslib.edtsds.list.radiobuttonlist

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.edtsds.list.checkmenu.RadioButtonListDelegate

class RadioButtonListView<T>: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val _adapter = RadioButtonAdapter<T>()

    var gravity: Int = Gravity.START
        set(value) {
            field = value
            _adapter.gravity = value
        }

    var delegate: RadioButtonListDelegate<T>? = null
        set(value) {
            field = value
            _adapter.menuDelegate = delegate
        }
    var data = listOf<T>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            _adapter.list = value.toMutableList()
            _adapter.notifyDataSetChanged()
        }
    var selectedIndex = 0
        set(value) {
            field = value
            _adapter.select(value)
        }

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = _adapter
    }
}