package id.co.edtslib.edtsds.bottom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.Window
import androidx.core.view.WindowCompat
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.Util.applyWindowInset
import id.co.edtslib.edtsds.databinding.DialogBottomLayoutBinding

open class BottomLayoutDialog(context: Context, themeResId: Int): Dialog(context, themeResId) {
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
                          consumeBottomInset: Boolean = true,
                          matchActivityNavigationBar: Boolean = true): BottomLayoutDialog {
            val dialog = BottomLayoutDialog(context, themeResId)
            dialog.matchActivityNavigationBar = matchActivityNavigationBar
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
                    dialog.dismiss()
                }

                override fun onCollapse() {
                    delegate?.onCollapse()
                }

                override fun onExpand() {
                    delegate?.onExpand()
                }
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

        fun showTray(context: Context, title: String, contentView: View, titleView: View? = null,
                     titleDivider: Boolean = true, popup: Boolean = false,
                     canceledOnTouchOutside: Boolean = true, isOverlay: Boolean? = true,
                     height: Int = LayoutParams.WRAP_CONTENT,
                     themeResId: Int = R.style.BottomLayoutDialog,
                     consumeBottomInset: Boolean = true,
                     matchActivityNavigationBar: Boolean = true) =
            showSwipeTray(context, title, contentView, tray = false, cancelable = true,
                titleView = titleView, titleDivider = titleDivider, popup = popup,
                themeResId = themeResId,
                canceledOnTouchOutside = canceledOnTouchOutside,
                isOverlay = isOverlay,
                height = height,
                consumeBottomInset = consumeBottomInset,
                matchActivityNavigationBar = matchActivityNavigationBar)

        fun close() {
            try {
                dialog.dismiss()
            }
            catch (_: Exception) {

            }
        }
    }

    val binding = DialogBottomLayoutBinding.inflate(layoutInflater)

    // true: navigation bar follows the host activity; false: dialog window default (previous behavior)
    var matchActivityNavigationBar = true

    fun close() {
        try {
            dismiss()
        }
        catch (ignore: Exception) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
        if (matchActivityNavigationBar) {
            applyNavigationBarFromActivity()
        }

        binding.bottomLayout.showAnimate()
    }

    // Keep the navigation bar looking like the host activity's instead of the dialog window default
    private fun applyNavigationBarFromActivity() {
        val dialogWindow = window ?: return
        val activityWindow = findActivity(context)?.window ?: return

        dialogWindow.navigationBarColor = activityWindow.navigationBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            dialogWindow.navigationBarDividerColor = activityWindow.navigationBarDividerColor
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dialogWindow.isNavigationBarContrastEnforced = activityWindow.isNavigationBarContrastEnforced
        }

        val activityController = WindowCompat.getInsetsController(activityWindow, activityWindow.decorView)
        WindowCompat.getInsetsController(dialogWindow, dialogWindow.decorView)
            .isAppearanceLightNavigationBars = activityController.isAppearanceLightNavigationBars
    }

    private fun findActivity(context: Context): Activity? {
        var ctx: Context? = context
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return ctx
            ctx = ctx.baseContext
        }
        return null
    }

    override fun onBackPressed() {
        close()
    }
}