package id.co.edtslib.edtsds.myapplication

import android.content.Context
import android.util.AttributeSet
import id.co.edtslib.edtsds.stepper2.Stepper2View

class TestStepperView: Stepper2View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun expand() {
    }
}