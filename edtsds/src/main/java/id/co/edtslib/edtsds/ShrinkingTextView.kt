package id.co.edtslib.edtsds

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

open class ShrinkingTextView: AppCompatTextView {
    var line = 1
        set(value) {
            field = value
            check()
        }

    var step = 0f
        set(value) {
            field = value
            if (value > 0f) {
                check()
            }
        }

    private var exp = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        check()
    }

    fun check() {
        exp = 0
        if (step == 0f) {
            step = resources.getDimensionPixelSize(R.dimen.dimen_half_dp).toFloat()
        }
        else {
            doCheck()
        }
    }

    private fun doCheck() {
        if (exp < 100) {
            postDelayed({
                if (lineCount > line) {
                    val t = textSize
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, t-step)

                    doCheck()
                }
            }, 100)

            exp++
        }
    }
}