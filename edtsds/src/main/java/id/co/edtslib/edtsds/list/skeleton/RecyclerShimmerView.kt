package id.co.edtslib.edtsds.list.skeleton

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.SkeletonMenuBinding

class RecyclerShimmerView: FrameLayout {
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

    var layout = R.layout.skeleton_menu_item
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (value != 0) {
                field = value
            }
            else {
                field = R.layout.skeleton_menu_item
            }

            adapter = RecyclerShimmerAdapter(field)

            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = adapter
        }

    var count = 10

    private val binding = SkeletonMenuBinding.inflate(LayoutInflater.from(context), this, true)
    private var adapter: RecyclerShimmerAdapter? = null

    private fun init(attrs: AttributeSet?) {

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RecyclerSkeletonView,
                0, 0
            )

            count = a.getInt(R.styleable.RecyclerSkeletonView_count, 10)
            layout = a.getResourceId(R.styleable.RecyclerSkeletonView_layout, 0)

            a.recycle()
        }
        else {
            layout = R.layout.skeleton_menu_item
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun show() {
        isVisible = true
        adapter?.count = count

        adapter?.notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun hide() {
        adapter?.count = 0
        adapter?.notifyDataSetChanged()

        isVisible = false

    }
}