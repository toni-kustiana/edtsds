package id.co.edtslib.edtsds.list

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

    protected abstract fun createAdapter(): BaseRecyclerViewAdapter<viewBinding, T>

    private fun init(attrs: AttributeSet?) {
        adapter = createAdapter()

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val startSnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView(this)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingItemView,
                0, 0
            )

            val size = a.getDimensionPixelSize(R.styleable.SlidingItemView_itemSpace,
                resources.getDimensionPixelSize(R.dimen.dimen_8dp))

            addItemDecoration(ItemDecoration(size))

            a.recycle()
        }
    }
}