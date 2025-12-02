package id.co.edtslib.edtsds.stepper2

interface Stepper2Delegate {
    fun onValueChanged(view: Stepper2View, value: Int)
    fun onReachMax(view: Stepper2View)
    fun onReachMin(view: Stepper2View)
    fun onExpand(view: Stepper2View, value: Int)
}