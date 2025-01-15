package id.co.edtslib.edtsds.percentagebarview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import id.co.edtslib.edtsds.R
import kotlin.math.max

class PercentageBarView: View {
    private val paint = Paint()
    var colorActive = Color.BLUE
    var colorNoActive = Color.GRAY
    var colorIndicator = Color.BLUE
    var radius = 0f
    var margin = 0f

    var pct = 0f
        set(value) {
            field = value
            invalidate()
        }

    var indicator = false
        set(value) {
            field = value
            invalidate()
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
                R.styleable.PercentageBarView,
                0, 0
            )

            colorNoActive = a.getColor(R.styleable.PercentageBarView_colorNoActive, Color.GRAY)
            colorActive = a.getColor(R.styleable.PercentageBarView_colorActive, Color.BLUE)
            colorIndicator = a.getColor(R.styleable.PercentageBarView_colorIndicator, Color.BLUE)
            radius = a.getDimension(R.styleable.PercentageBarView_radius, 0f)
            indicator = a.getBoolean(R.styleable.PercentageBarView_indicator, false)
            margin = a.getDimension(R.styleable.PercentageBarView_marginActive, 0f)

            a.recycle()
        }
    }

    fun setPercentage(pct: Float) {
        this.pct = pct
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = colorNoActive
        paint.style = Paint.Style.FILL

        val h = if (indicator) {
            height.toFloat()/2f
        }
        else {
            height.toFloat()
        }

        val top = if (indicator) {
            height.toFloat()/4f
        }
        else {
            0f
        }

        canvas?.drawRoundRect(0f, top-margin, width.toFloat(), top+h+margin,
            radius, radius, paint)

        val w = pct*width

        paint.color = colorActive
        canvas?.drawRoundRect(0f, top, w, top+h,
            radius, radius, paint)

        if (indicator) {
            paint.color = colorIndicator
            canvas?.drawCircle(
                max( w - height.toFloat() / 2, height.toFloat()/2),
                height.toFloat() / 2,
                height.toFloat() / 2,
                paint
            )
        }
    }
}