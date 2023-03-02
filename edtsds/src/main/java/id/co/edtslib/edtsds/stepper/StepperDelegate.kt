package id.co.edtslib.edtsds.stepper

interface StepperDelegate {
    fun onValueChanged(value: Int)
    fun onSubmit(value: Int)
    fun onErrorMax()
    fun onErrorMin()
}