package id.co.edtslib.edtsds.otpverification

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewOtpVerificationBinding
import id.co.edtslib.edtsds.otpremainingview.OtpRemainingDelegate
import id.co.edtslib.edtsds.otpview.OtpDelegate

class OtpVerificationView: FrameLayout {

    var delegate: OtpVerificationDelegate? = null

    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    val binding: ViewOtpVerificationBinding =
        ViewOtpVerificationBinding.inflate(LayoutInflater.from(context), this, true)

    val otpView = binding.otpView
    val otpRemainingView = binding.otpRemainingView
    var error: String? = null
        set(value) {
            field = value

            binding.tvError.isVisible = value?.isNotEmpty() == true
            binding.tvError.text = value

            otpView.isError = value?.isNotEmpty() == true
        }

    private fun init(attrs: AttributeSet?) {
        binding.bvVerification.isEnabled = false
        binding.tvError.isVisible = false

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.OtpVerificationView,
                0, 0
            )

            binding.otpView.delegate = object : OtpDelegate {
                override fun onCompleted(pin: String) {
                    binding.bvVerification.isEnabled = true
                    binding.bvVerification.setOnClickListener {
                        delegate?.onSubmit(pin)
                    }
                }

                override fun onTextChanged(text: String) {
                    binding.bvVerification.isEnabled = text.length == binding.otpView.length
                }
            }

            binding.otpRemainingView.delegate = object : OtpRemainingDelegate {
                override fun onExpired() {
                    delegate?.onExpired()
                }
            }
            binding.otpRemainingView.interval = a.getInt(R.styleable.OtpRemainingView_otpInterval, 0).toLong()

            a.recycle()
        }
    }
}