package id.co.edtslib.edtsds.shimmer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout
import id.co.edtslib.edtsds.R

class ShimmerView: FrameLayout {
    constructor(context: Context) : super(context)
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

    var shimmerLayout: ShimmerFrameLayout? = null
    var shimmerView: View? = null

    var shimmerWidth = 0
        set(value) {
            field = value

            if (value > 0) {
                val layoutParams = shimmerLayout?.layoutParams
                layoutParams?.width = value
            }
        }

    var shimmerHeight = 0
        set(value) {
            field = value

            if (value > 0) {
                val layoutParams = shimmerLayout?.layoutParams
                layoutParams?.height = value
            }
        }

    var shimmerBackground = 0
        set(value) {
            field = value
            if (value != 0) {
                shimmerView?.setBackgroundResource(value)
            }
        }

    private fun init(attrs: AttributeSet?) {
        shimmerLayout = ShimmerFrameLayout(context)
        addView(shimmerLayout)

        val layoutParams = shimmerLayout?.layoutParams as LayoutParams
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.WRAP_CONTENT

        shimmerLayout?.layoutParams = layoutParams

        shimmerView = View(context)
        shimmerLayout?.addView(shimmerView)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ShimmerView,
                0, 0
            )

            shimmerWidth = (a.getDimension(R.styleable.ShimmerView_shimmerWidth, 0f)).toInt()
            shimmerHeight = (a.getDimension(R.styleable.ShimmerView_shimmerHeight, 0f)).toInt()
            shimmerBackground = a.getResourceId(R.styleable.ShimmerView_shimmerBackground, 0)

            if (a.getBoolean(R.styleable.ShimmerView_shimmerAutoStart, false)) {
                post {
                    showShimmer()
                }
            }
            else {
                hideShimmer()
            }

            a.recycle()
        }
        else {
            hideShimmer()
        }
    }

    fun showShimmer() {
        if (shimmerWidth == 0 && shimmerHeight == 0) {
            post {
                shimmerWidth = getChildAt(childCount-1).width
                shimmerHeight = getChildAt(childCount-1).height

                doShowShimmer()
            }
        }
        else
        if (shimmerWidth == 0) {
            post {
                shimmerWidth = getChildAt(childCount-1).width
                doShowShimmer()
            }
        }
        else
            if (shimmerHeight == 0) {
                post {
                    shimmerHeight = getChildAt(childCount-1).height
                    doShowShimmer()
                }
            }
        else {
                doShowShimmer()
            }
    }

    private fun doShowShimmer() {
        getChildAt(childCount-1).isVisible = false

        shimmerView?.isVisible = true
        shimmerLayout?.startShimmer()
    }

    fun hideShimmer() {
        shimmerLayout?.stopShimmer()
        shimmerView?.isVisible = false

        getChildAt(childCount-1).isVisible = true

    }
}