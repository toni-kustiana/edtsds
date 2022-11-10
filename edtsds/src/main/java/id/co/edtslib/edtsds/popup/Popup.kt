package id.co.edtslib.edtsds.popup

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.DialogPopupBinding

class Popup private constructor(context: Context, private val view: View?) : Dialog(context) {
    enum class Orientation {
        Horizontal, Vertical
    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        private var popup: Popup? = null
        var dismissible = false

        fun show(activity: FragmentActivity, title: String?, message: String,
                 positiveButton: String?, positiveClickListener: OnClickListener?) {
            show(activity, title, message, positiveButton, null,
                positiveClickListener, null)
        }

        fun show(activity: FragmentActivity, title: String?, message: String,
                 positiveButton: String?, negativeButton: String?,
                 positiveClickListener: OnClickListener?, negativeClickListener: OnClickListener?,
                 orientation: Orientation = Orientation.Horizontal) {

            if (popup == null) {
                popup = Popup(activity, null)

                popup?.binding?.tvTitle?.isVisible = title?.isNotEmpty() == true
                popup?.binding?.tvTitle?.text = title

                popup?.binding?.tvMessage?.text = message

                popup?.binding?.bvNegative?.isVisible = negativeButton != null
                popup?.binding?.bvNegative?.text = negativeButton

                val negativeClick = negativeClickListener
                    ?: OnClickListener {
                        popup?.dismiss()
                    }
                popup?.binding?.bvNegative?.setOnClickListener(negativeClick)


                popup?.binding?.bvPositive?.text = positiveButton
                val positiveClick = positiveClickListener
                    ?: OnClickListener {
                        popup?.dismiss()
                    }
                popup?.binding?.bvPositive?.setOnClickListener(positiveClick)


                if (orientation == Orientation.Vertical) {
                    popup?.binding?.llButton?.orientation = LinearLayoutCompat.VERTICAL

                    val lp1 = popup?.binding?.bvNegative?.layoutParams as LinearLayoutCompat.LayoutParams?
                    lp1?.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    lp1?.marginEnd = 0

                    val lp2 = popup?.binding?.bvPositive?.layoutParams as LinearLayoutCompat.LayoutParams?
                    lp2?.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    lp2?.marginStart = 0
                    lp2?.topMargin = activity.resources.getDimensionPixelSize(R.dimen.dimen_8dp)

                    // tukar posisi negative dengan positive

                    popup?.binding?.bvNegative?.variant = ButtonView.ButtonVariant.Primary
                    popup?.binding?.bvNegative?.text = positiveButton
                    popup?.binding?.bvNegative?.setOnClickListener(positiveClick)

                    popup?.binding?.bvPositive?.variant = ButtonView.ButtonVariant.Outline
                    popup?.binding?.bvPositive?.text = negativeButton
                    popup?.binding?.bvPositive?.setOnClickListener(negativeClick)

                }

                popup?.show()

                val width = Resources.getSystem().displayMetrics.widthPixels * 0.9f
                popup?.window?.setLayout(
                    width.toInt(),
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
        }

        fun show(view: View) {
            popup = Popup(view.context, view)
            popup?.show()

            val width = Resources.getSystem().displayMetrics.widthPixels * 0.9f
            popup?.window?.setLayout(
                width.toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        fun close() {
            try {
                popup?.dismiss()
                popup = null
            }
            catch (e: Exception) {
                // ignore it
            }
        }
    }

    val binding = DialogPopupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(view ?: binding.root)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)

        setCancelable(dismissible)
    }
}