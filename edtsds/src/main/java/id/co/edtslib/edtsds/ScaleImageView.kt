package id.co.edtslib.edtsds

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ScaleImageView: AppCompatImageView {
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

    var scale = 1f
        set(value) {
            field = value
            post {
                layoutParams.height = (value*width).toInt()
                layoutParams = layoutParams
            }
        }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ScaleImageView,
                0, 0
            )

            scale = a.getFloat(R.styleable.ScaleImageView_scale, 1f)

            a.recycle()
        }
    }
}