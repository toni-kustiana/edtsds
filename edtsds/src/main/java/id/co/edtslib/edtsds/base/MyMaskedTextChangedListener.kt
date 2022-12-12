package id.co.edtslib.edtsds.base

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy

class MyMaskedTextChangedListener: MaskedTextChangedListener {
    /**
     * Convenience constructor.
     */
    constructor(format: String, field: EditText, valueListener: MyValueListener?) :
            this(format, field, null, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(format: String, field: EditText, listener: TextWatcher?, valueListener: MyValueListener?) :
            this(format, true, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(
        format: String, autocomplete: Boolean, field: EditText, listener: TextWatcher?,
        valueListener: MyValueListener?
    ) :
            super(
                format, emptyList(), emptyList(), AffinityCalculationStrategy.WHOLE_STRING,
                autocomplete, false, field, listener, valueListener
            )

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        try {
            if (valueListener is MyValueListener) {
                (valueListener as MyValueListener).onFocussed(view, hasFocus)
            }
            super.onFocusChange(view, hasFocus)
        }
        catch (ignore: SecurityException) {

        }
    }
}