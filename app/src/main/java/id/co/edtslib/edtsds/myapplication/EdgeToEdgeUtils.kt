package id.co.edtslib.edtsds.myapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

object EdgeToEdgeUtils {
    var bottomInset = 0
    fun AppCompatActivity.applyInsetAndStatusBarHandling(
        rootView: View,
        consumeTopInset: Boolean = true,
        consumeBottomInset: Boolean = true,
        statusBarDrawable: Drawable = ContextCompat.getColor(
            this,
            R.color.white
        ).toDrawable(),
        result: (View?, WindowInsetsCompat) -> Unit
    ) {
        var statusBarView: View? = null

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val statusBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())

            val tv = TypedValue()
            theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
            val actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)

            if (statusBarView == null && consumeTopInset) {
                statusBarView = View(this)

                setStatusBarColor(statusBarDrawable, statusBarView)

                val subtractHeight = if (
                    statusBarInset.top > actionBarHeight
                ) actionBarHeight else 0

                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarInset.top - subtractHeight
                )
                window?.addContentView(statusBarView, params)
            }

            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val subtractHeight = if (
                    supportActionBar?.isShowing == false &&
                    statusBarInset.top > actionBarHeight
                ) actionBarHeight else 0

                if (consumeTopInset) topMargin = statusBarInset.top - subtractHeight
                if (consumeBottomInset) bottomMargin = navBarInset.bottom
                bottomInset = navBarInset.bottom
            }

            result(statusBarView, windowInsets)
            WindowInsetsCompat.CONSUMED
        }
    }

    fun AppCompatActivity.setStatusBarColor(
        drawable: Drawable,
        statusBarView: View
    ) {
        statusBarView.background = drawable

        val color = drawable.extractDominantColor()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30+
            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = color.isLightColor() == true
        } else {
            // API 23–29
            @Suppress("DEPRECATION")
            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = if (color.isLightColor()) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    fun Drawable.extractDominantColor(): Int {
        return when (this) {
            is ColorDrawable -> this.color
            else -> {
                // Convert drawable to Bitmap and extract center pixel color as an approximation
                val bitmap = createBitmap(1, 1)
                val canvas = Canvas(bitmap)
                setBounds(0, 0, 1, 1)
                draw(canvas)
                bitmap[0, 0]
            }
        }
    }

    /**
     * Extension function to determine if a color is light based on its luminance.
     * Returns true if the color is light (suitable for dark icons),
     * false if the color is dark (suitable for light icons).
     */
    fun @receiver:ColorInt Int.isLightColor(): Boolean {
        val red = Color.red(this)
        val green = Color.green(this)
        val blue = Color.blue(this)

        // Standard luminance formula for perceived brightness
        val luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255

        return luminance > 0.5 // true means light color
    }
}