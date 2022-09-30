package id.co.edtslib.edtsds

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object Util {
    private val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
        .setDuration(2000) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.9f) //the alpha of the underlying children
        .setHighlightAlpha(0.93f) // the shimmer alpha amount
        .setWidthRatio(1.0F)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()


    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}