package id.co.edtslib.edtsds.percentagebarview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import id.co.edtslib.edtsds.R

class PercentageBarView: View {
    private val paint = Paint()
    private var pct = 0f
    var colorActive = Color.BLUE
    var colorNoActive = Color.GRAY
    var radius = 0f

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
            radius = a.getDimension(R.styleable.PercentageBarView_radius, 0f)

            a.recycle()
        }
    }

    fun setPercentage(pct: Float) {
        this.pct = pct
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = colorNoActive
        paint.style = Paint.Style.FILL

        canvas?.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(),
            radius, radius, paint)

        val w = pct*width

        paint.color = colorActive
        canvas?.drawRoundRect(0f, 0f, w, height.toFloat(),
            radius, radius, paint)
    }
}