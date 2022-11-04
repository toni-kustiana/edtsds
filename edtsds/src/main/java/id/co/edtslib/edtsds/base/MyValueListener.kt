package id.co.edtslib.edtsds.base

import android.view.View
import com.redmadrobot.inputmask.MaskedTextChangedListener

interface MyValueListener: MaskedTextChangedListener.ValueListener {
    fun onFocussed(view: View?, hasFocus: Boolean)
}