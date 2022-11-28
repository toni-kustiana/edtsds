package id.co.edtslib.edtsds.list.skeleton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout
import id.co.edtslib.edtsds.databinding.SkeletonMenuBinding

class MenuSkeletonView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val binding = SkeletonMenuBinding.inflate(LayoutInflater.from(context), this, true)

    fun show() {
        isVisible = true

        for (i in 0 until binding.linearLayout.childCount) {
            val view = binding.linearLayout.getChildAt(i)
            if (view is ShimmerFrameLayout) {
                val shimmerFrameLayout = view as ShimmerFrameLayout
                if (shimmerFrameLayout.childCount > 0) {
                    shimmerFrameLayout.getChildAt(0).isVisible = true
                }
                shimmerFrameLayout.startShimmer()

            }
        }
    }

    fun hide() {
        for (i in 0 until binding.linearLayout.childCount) {
            val view = binding.linearLayout.getChildAt(i)
            if (view is ShimmerFrameLayout) {
                val shimmerFrameLayout = view as ShimmerFrameLayout
                shimmerFrameLayout.stopShimmer()

                if (shimmerFrameLayout.childCount > 0) {
                    shimmerFrameLayout.getChildAt(0).isVisible = false
                }

            }
        }

        isVisible = false

    }
}