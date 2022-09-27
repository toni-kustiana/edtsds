package id.co.edtslib.edtsds.list

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.edtsds.R

abstract class SlidingItemView<viewBinding: ViewBinding, T>: RecyclerView {
    class ItemDecoration(private val size: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)
            outRect.right = if (position+1 == parent.adapter?.itemCount) 0 else size
        }
    }

    private var itemPreviewSize = 0
    private var lPaddingEnd =0
    private var runnable: Runnable? = null

    var data = listOf<T>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (isDifferent(value)) {
                field = value

                val adapter = adapter as BaseRecyclerViewAdapter<*, T>
                adapter.list.clear()
                adapter.list.addAll(value)
                adapter.notifyDataSetChanged()

                if (adapter.list.size == 1) {
                    setPadding(lPaddingEnd, paddingTop, lPaddingEnd, paddingBottom)
                }
            }
        }

    private fun isDifferent(list: List<T>): Boolean {
        if (data.size != list.size) {
            return true
        }
        else {
            for ((i, item) in list.withIndex()) {
                if (! isEqual(data[i], item)) {
                    return true
                }
            }

            return false
        }
    }

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

    var snap = false
        set(value) {
            field = value
            if (value) {
                val startSnapHelper = StartSnapHelper()
                startSnapHelper.attachToRecyclerView(this)
            }
        }

    protected abstract fun isEqual(a: T, b: T): Boolean
    protected abstract fun createAdapter(): BaseRecyclerViewAdapter<viewBinding, T>

    protected open fun init(attrs: AttributeSet?) {
        adapter = createAdapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        clipToPadding = false

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingItemView,
                0, 0
            )

            val size = a.getDimensionPixelSize(R.styleable.SlidingItemView_itemSpace,
                resources.getDimensionPixelSize(R.dimen.dimen_8dp))

            lPaddingEnd = paddingStart
            itemPreviewSize = a.getDimensionPixelSize(R.styleable.SlidingItemView_itemPreviewSize,
                0)

            snap = a.getBoolean(R.styleable.SlidingItemView_snap,
                false)

            if (itemPreviewSize > 0) {
                setPadding(paddingStart, paddingTop, itemPreviewSize, paddingBottom)
            }
            addItemDecoration(ItemDecoration(size))

            a.recycle()
        }
    }

    override fun onScrollStateChanged(state: Int) {
        if (state == SCROLL_STATE_IDLE ) {
            if (itemPreviewSize > lPaddingEnd) {
                setPadding()
            }
        }
        super.onScrollStateChanged(state)
    }

    private fun setPadding() {
        if (runnable != null) {
            removeCallbacks(runnable)
        }

        runnable = Runnable {
            val adapter = adapter as BaseRecyclerViewAdapter<*, *>
            if (adapter.list.size > 1) {

                val linearLayoutManager = layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (position + 1 >= adapter.list.size) {
                    val delta = itemPreviewSize - lPaddingEnd
                    if (paddingEnd != lPaddingEnd) {
                        setPadding(paddingStart + delta, paddingTop, lPaddingEnd, paddingBottom)
                    }
                } else {
                    if (paddingEnd != itemPreviewSize) {
                        setPadding(lPaddingEnd, paddingTop, itemPreviewSize, paddingBottom)
                    }
                }
            }
        }

        postDelayed(runnable, 500)
    }
}