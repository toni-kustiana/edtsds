package id.co.edtslib.edtsds.stepper

interface StepperDelegate {
    fun onChangeValue(value: Int)
    fun onErrorMax()
    fun onErrorMin()
}