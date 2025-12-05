package id.co.edtslib.edtsds.bottom

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.Window
import androidx.activity.ComponentDialog
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.Util.applyWindowInset
import id.co.edtslib.edtsds.databinding.DialogBottomLayoutBinding

open class BottomLayoutDialog(context: Context, themeResId: Int): ComponentDialog(context, themeResId) {
    var isCleanDelegate: Boolean = false
    companion object {
        private lateinit var dialog: BottomLayoutDialog
        var delegate: BottomLayoutDelegate? = null

        fun showSwipeTray(context: Context, title: String, contentView: View,
                          tray: Boolean = true, cancelable: Boolean = false,
                          titleView: View? = null, titleDivider: Boolean = true,
                          popup: Boolean = false,
                          height: Int = LayoutParams.WRAP_CONTENT,
                          canceledOnTouchOutside: Boolean = true, isOverlay: Boolean? = tray,
                          themeResId: Int = R.style.BottomLayoutDialog,
                          consumeBottomInset: Boolean = true): BottomLayoutDialog {
            val dialog = BottomLayoutDialog(context, themeResId)
            dialog.binding.bottomLayout.title = title
            dialog.binding.bottomLayout.tray = tray
            dialog.binding.bottomLayout.cancelable = cancelable
            dialog.binding.bottomLayout.contentView = contentView
            dialog.binding.bottomLayout.titleView = titleView
            dialog.binding.bottomLayout.titleDivider = titleDivider
            dialog.binding.bottomLayout.popup = popup
            dialog.binding.bottomLayout.canceledOnTouchOutside = canceledOnTouchOutside
            dialog.binding.bottomLayout.isOverlay = isOverlay
            dialog.binding.bottomLayout.delegate = object : BottomLayoutDelegate {
                override fun onDismiss() {
                    dismissAction(dialog)
                }

                override fun onCollapse() {
                    delegate?.onCollapse()
                    if (dialog.isCleanDelegate) delegate = null
                }

                override fun onExpand() {
                    delegate?.onExpand()
                }

                override fun onClose() {
                    delegate?.onClose()
                    if (dialog.isCleanDelegate) delegate = null
                }

                override fun onInterceptDismiss() = delegate?.onInterceptDismiss() ?: false
            }

            dialog.setCancelable(cancelable)
            dialog.setCanceledOnTouchOutside(false)
            dialog.applyWindowInset(contentView, consumeBottomInset){ bottomInset->
                val isCustomHeight = !(height == LayoutParams.WRAP_CONTENT || height == LayoutParams.MATCH_PARENT)
                val finalHeight = if (isCustomHeight) height + bottomInset  else height
                dialog.binding.bottomLayout.bottomHeight = finalHeight
            }
            dialog.show()

            BottomLayoutDialog.dialog = dialog

            return dialog
        }

        private fun dismissAction(dialog: BottomLayoutDialog) {
            dialog.dismiss()
            delegate?.onDismiss()
            if (dialog.isCleanDelegate) delegate = null
        }

        fun showTray(context: Context, title: String, contentView: View, titleView: View? = null,
                     titleDivider: Boolean = true, popup: Boolean = false,
                     canceledOnTouchOutside: Boolean = true, isOverlay: Boolean? = true,
                     height: Int = LayoutParams.WRAP_CONTENT,
                     themeResId: Int = R.style.BottomLayoutDialog,
                     consumeBottomInset: Boolean = true) =
            showSwipeTray(context, title, contentView, tray = false, cancelable = true,
                titleView = titleView, titleDivider = titleDivider, popup = popup,
                themeResId = themeResId,
                canceledOnTouchOutside = canceledOnTouchOutside,
                isOverlay = isOverlay,
                height = height,
                consumeBottomInset = consumeBottomInset)

        fun close() {
            try {
                dialog.binding.bottomLayout.tryDismiss(shouldCheckDismiss = false){
                    dismissAction(dialog)
                }
            }
            catch (ignore: Exception) {

            }
        }
    }

    val binding = DialogBottomLayoutBinding.inflate(layoutInflater)

    fun close() {
        try {
            binding.bottomLayout.tryDismiss(shouldCheckDismiss = false){
                dismissAction(dialog)
            }
        }
        catch (ignore: Exception) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)

        binding.bottomLayout.showAnimate()
    }

    override fun onBackPressed() {
        close()
    }
}