package id.co.edtslib.edtsds.pagingnavigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import id.co.edtslib.edtsds.R

class PagingNavigationView : LinearLayoutCompat {
    var delegate: PagingNavigationDelegate? = null
    var space = 0f
    var shapeSize = 0f
    var shapeSelectedWidth = 0f
    var shapeSelectedHeight = 0f
    var shapeResId = 0

    // set size of pager
    var count: Int = 0
        set(value) {
            field = value

            removeAllViews()
            repeat(value) {
                val view = View(context)
                addView(view)

                view.setOnClickListener { _ ->
                    selectedIndex = it
                    delegate?.onSelected(it)
                }
                view.setBackgroundResource(shapeResId)

                val layoutParams = view.layoutParams as LayoutParams
                layoutParams.width = shapeSize.toInt()
                layoutParams.height = shapeSize.toInt()
                if (it+1 < count) {
                    layoutParams.marginEnd = space.toInt()
                }
            }
        }

    // selected index of shape
    var selectedIndex: Int = -1
        set(value) {
            field = value

            repeat(childCount) {
                val view = getChildAt(it)
                if (shapeSize != shapeSelectedWidth || shapeSize != shapeSelectedHeight) {
                    val width = if (it == value) shapeSelectedWidth else shapeSize
                    val height = if (it == value) shapeSelectedHeight else shapeSize

                    val layoutParams = view.layoutParams as LayoutParams
                    layoutParams.width = width.toInt()
                    layoutParams.height = height.toInt()

                    view.layoutParams = layoutParams
                }

                view.isSelected = it == value
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

    private fun init(attrs: AttributeSet?) {

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PagingNavigationView,
                0, 0
            )

            space = a.getDimension(R.styleable.PagingNavigationView_space,
                resources.getDimensionPixelSize(R.dimen.dimen_8dp).toFloat())
            shapeSize = a.getDimension(R.styleable.PagingNavigationView_shapeSize,
                resources.getDimensionPixelSize(R.dimen.dimen_4dp).toFloat())
            shapeSelectedWidth = a.getDimension(R.styleable.PagingNavigationView_shapeSelectedWidth,
                shapeSize)
            shapeSelectedHeight = a.getDimension(R.styleable.PagingNavigationView_shapeSelectedHeight,
                shapeSize)

            shapeResId = a.getResourceId(R.styleable.PagingNavigationView_shape,
                R.drawable.bg_navigation)

            val lSize = a.getInteger(R.styleable.PagingNavigationView_count, 0)
            if (lSize > 0) {
                count = lSize
            }

            a.recycle()
        }
    }
}