package id.co.edtslib.edtsds.otpremainingview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewOtpRemainingBinding
import java.util.*

class OtpRemainingView: FrameLayout {
    enum class FormatType {
        Clock, Text
    }

    var interval = 0L
    private var startTime = 0L
    private var format = FormatType.Clock
    private var counterText: String? = null
    private var sHour: String? = null
    private var sMinute: String? = null
    private var sSecond: String? = null

    private var runnable: Runnable? = null
    var delegate: OtpRemainingDelegate? = null

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


    private val binding: ViewOtpRemainingBinding =
        ViewOtpRemainingBinding.inflate(LayoutInflater.from(context), this, true)


    private fun init(attrs: AttributeSet?) {

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.OtpRemainingView,
                0, 0
            )

            counterText = a.getString(R.styleable.OtpRemainingView_otpRemainingText)
            interval = a.getInt(R.styleable.OtpRemainingView_otpInterval, 0).toLong()
            format = FormatType.values()[a.getInteger(R.styleable.OtpRemainingView_otpRemainingFormat, 0)]

            sHour = a.getString(R.styleable.OtpRemainingView_otpRemainingHour)
            sMinute = a.getString(R.styleable.OtpRemainingView_otpRemainingMinute)
            sSecond = a.getString(R.styleable.OtpRemainingView_otpRemainingSecond)

            a.recycle()
        }
    }

    fun setTextStyle(style: Int) {
        TextViewCompat.setTextAppearance(binding.tvCounter, style)
    }

    fun start() {
        binding.tvCounter.isVisible = true
        startTime = Date().time
        reset()
    }

    private fun stop() {
        if (runnable != null) {
            binding.tvCounter.removeCallbacks(runnable)
            runnable = null
        }
    }

    fun expired() {
        stop()
        binding.tvCounter.isVisible = false
    }

    fun reset() {
        showRemain(interval)

        timer()
    }

    private fun timer() {
        stop()

        runnable = Runnable {
            val remain = interval - ((Date().time-startTime)/1000)
            if (remain > 0) {
                showRemain(remain)
                timer()
            }
            else {
                binding.tvCounter.isVisible = false
                delegate?.onExpired()
            }
        }
        binding.tvCounter.postDelayed(runnable, 1000L)
    }

    private fun showRemain(interval: Long) {
        if (counterText != null) {
            val hour = interval / 3600
            val minute = (interval % 3600) / 60
            val second = (interval % 3600) % 60

            val remain = if (format == FormatType.Text) {
                if (hour > 0) {
                    if (minute > 0 && second > 0) {
                        String.format("%d %s %d %s %d %s", hour, sHour, minute, sMinute,
                            second, sSecond)
                    }
                    else if (minute > 0) {
                        String.format("%d %s %d %s", hour, sHour, minute, sMinute)
                    }
                    else if (second > 0) {
                        String.format("%d %s %d %s", hour, sHour, second, sSecond)
                    }
                    else {
                        String.format("%d %s", hour, sHour)
                    }
                }
                else
                    if (minute > 0) {
                        if (second > 0) {
                            String.format("%d %s %d %s", minute, sMinute, second, sSecond)
                        }
                        else {
                            String.format("%d %s", minute, sMinute)
                        }
                    }
                    else {
                        String.format("%d %s", second, sSecond)
                    }
            }
            else {
                when {
                    hour > 0 -> {
                        String.format("%02d:%02d:%02d", hour, minute, second)
                    }
                    else -> {
                        String.format("%02d:%02d", minute, second)
                    }
                }
            }

            binding.tvCounter.text = HtmlCompat.fromHtml(String.format(counterText!!, remain),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}