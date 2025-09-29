package id.co.edtslib.edtsds

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnAttach
import androidx.core.view.updateLayoutParams
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

    fun isValidContext(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            if (context.isDestroyed || context.isFinishing) {
                return false
            }
        }
        return true
    }

    fun Dialog.applyWindowInset(
        dialogRoot: View,
        consumeBottomInset: Boolean = true,
        bottomInset: (Int)->Unit
    ){
        ViewCompat.setOnApplyWindowInsetsListener(dialogRoot){ view, windowInsets ->
            val navBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                if (consumeBottomInset) bottomMargin = navBarInset.bottom
            }
            bottomInset(navBarInset.bottom)
            WindowInsetsCompat.CONSUMED
        }
        // Ensure insets are requested after the view is attached
        dialogRoot.doOnAttach {
            ViewCompat.requestApplyInsets(it)
        }
    }
}