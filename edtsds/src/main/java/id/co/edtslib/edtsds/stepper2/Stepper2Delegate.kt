package id.co.edtslib.edtsds.stepper2

interface Stepper2Delegate {
    fun onValueChanged(value: Int)
    fun onReachMax()
    fun onReachMin()
}