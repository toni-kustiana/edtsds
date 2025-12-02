package id.co.edtslib.edtsds.textfield.time

import java.time.LocalTime

interface TimeFieldDelegate {
    fun onTimeChanged(time: LocalTime, format: String)
}