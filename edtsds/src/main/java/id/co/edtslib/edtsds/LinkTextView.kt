package id.co.edtslib.edtsds

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat

class LinkTextView: AppCompatTextView {
    private var isLink = false
    var link: String? = null
    private var linkColor = 0

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)

    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int ) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LinkTextView,
                defStyleAttr, 0
            )

            link = a.getString(R.styleable.LinkTextView_linkText)
            linkColor = a.getColor(R.styleable.LinkTextView_linkColor, 0)

            relayout()

            a.recycle()
        }
    }

    fun relayout() {
        if (link != null) {
            val text = HtmlCompat.fromHtml(text.toString(),0)

            val ss = SpannableString(text)
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isLink = true
                    performClick()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)

                    ds.color = linkColor
                }
            }

            val idx = text.indexOf(link!!)
            if (idx >= 0) {
                ss.setSpan(clickableSpan, idx, idx+link!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                highlightColor = Color.TRANSPARENT
                setText(ss)
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun performClick(): Boolean {
        return if (isLink) {
            isLink = false
            super.performClick()
        } else {
            false
        }
    }
}