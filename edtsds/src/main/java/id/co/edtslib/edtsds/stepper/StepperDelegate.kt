package id.co.edtslib.edtsds.stepper

import android.view.View

interface StepperDelegate {
    fun onValueChanged(value: Int, view: View?)
    fun onSubmit(value: Int, view: View?)
    fun onErrorMax(view: View?)
    fun onErrorMin(view: View?)
}