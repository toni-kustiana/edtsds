package id.co.edtslib.edtsds.list

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import id.co.edtslib.edtsds.R

class SlidingItemLayoutView: FrameLayout {
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

    private var imageView: AppCompatImageView? = null
    private var imageViewResId = 0
    private var drawableWidth = 0f
    private var slidingItemOffsetX = 0

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingItemLayoutView,
                0, 0
            )

            drawableWidth = a.getDimension(R.styleable.SlidingItemLayoutView_drawableWidth, 0f)
            imageViewResId = a.getResourceId(R.styleable.SlidingItemLayoutView_drawableStartCompat, 0)

            a.recycle()
        }
    }

    fun redraw() {
        if (imageViewResId != 0) {
            postDelayed( {
                val h = height

                if (imageView == null) {
                    imageView = AppCompatImageView(context)
                    addView(imageView, 0)

                    imageView?.scaleType = ImageView.ScaleType.FIT_XY
                    imageView?.setImageResource(imageViewResId)
                }

                val frameLayout = imageView?.layoutParams
                frameLayout?.width = drawableWidth.toInt()
                frameLayout?.height = h - paddingTop - paddingBottom

                if (childCount > 1) {
                    val view = getChildAt(1)
                    if (view is SlidingItemView<*, *>) {
                        setScrollListener(view)
                    }
                }

            }, 500)
        }
    }

    private fun setScrollListener(slidingItemView: SlidingItemView<*, *>) {
        slidingItemOffsetX = slidingItemView.paddingStart

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            slidingItemView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    slidingItemOffsetX -= dx

                    val alpha = if (slidingItemView.paddingStart == 0) 1f else (slidingItemOffsetX*1.0f)/(slidingItemView.paddingStart*1.0f)
                    imageView?.alpha = if (alpha < 0f) 0f else if (alpha > 1f) 1f else alpha

                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }
    }

}