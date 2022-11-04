package id.co.edtslib.edtsds.textfield.date

import java.util.Date

interface DateFieldDelegate {
    fun onDateChanged(date: Date, format: String)
}