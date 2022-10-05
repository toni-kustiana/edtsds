package id.co.edtslib.edtsds

import android.animation.Animator
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.databinding.ViewErrorMessageBinding

object Alert {
    const val LENGTH_LONG = 1L
    const val LENGTH_SHORT = 0L

    fun show(context: Context, message: String, duration: Long = LENGTH_LONG, view: View? = null) {
        val params = WindowManager.LayoutParams()
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.format = PixelFormat.TRANSLUCENT
        params.gravity = Gravity.TOP
        params.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)


        val lView = if (view == null) {
            val binding: ViewErrorMessageBinding =
                ViewErrorMessageBinding.inflate(
                    LayoutInflater.from(context),
                    null, false
                )
            binding.textView.text = message
            binding.root.isInvisible = true

            binding.root
        }
        else {
            view
        }

        val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWM.addView(lView, params)

        lView.post {
            lView.translationY = -lView.height.toFloat()
            lView.isVisible = true
            lView.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    close(context, lView, duration)
                }

                override fun onAnimationCancel(p0: Animator) {
                    close(context, lView, duration)
                }

                override fun onAnimationRepeat(p0: Animator) {

                }

            }).translationY(0f).start()
        }
    }

    private fun close(context: Context, view: View, duration: Long) {
        val lDuration = if (duration == LENGTH_LONG) 5000L else if (duration == LENGTH_SHORT)  1000 else duration
        view.postDelayed({
            view.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    view.translationY = -view.height.toFloat()
                    val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    mWM.removeView(view)
                }

                override fun onAnimationCancel(p0: Animator) {
                    val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    mWM.removeView(view)
                }

                override fun onAnimationRepeat(p0: Animator) {

                }

            }).translationY(-view.height.toFloat()).start()
        }, lDuration)
    }
}