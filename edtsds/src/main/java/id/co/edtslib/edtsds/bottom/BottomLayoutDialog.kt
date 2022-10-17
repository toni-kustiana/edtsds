package id.co.edtslib.edtsds.bottom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.DialogBottomLayoutBinding

open class BottomLayoutDialog(context: Context):
    Dialog(context,R.style.BottomLayoutDialog) {
    companion object {
        lateinit var dialog: BottomLayoutDialog
        fun showSwipeTray(context: Context, title: String, contentView: View,
                          tray: Boolean = true, cancelable: Boolean = false,
                          titleView: View? = null, titleDivider: Boolean = true,
                          popup: Boolean = false) {
            dialog = BottomLayoutDialog(context)
            dialog.binding.bottomLayout.title = title
            dialog.binding.bottomLayout.tray = tray
            dialog.binding.bottomLayout.cancelable = cancelable
            dialog.binding.bottomLayout.contentView = contentView
            dialog.binding.bottomLayout.titleView = titleView
            dialog.binding.bottomLayout.titleDivider = titleDivider
            dialog.binding.bottomLayout.popup = popup
            dialog.binding.bottomLayout.delegate = object : BottomLayoutDelegate {
                override fun onDismiss() {
                    dialog.dismiss()
                }
            }

            dialog.setCancelable(cancelable)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }

        fun showTray(context: Context, title: String, contentView: View, titleView: View? = null,
                     titleDivider: Boolean = true, popup: Boolean = false) {
            showSwipeTray(context, title, contentView, tray = false, cancelable = true,
                titleView = titleView, titleDivider = titleDivider, popup = popup)
        }

        fun close() {
            try {
                dialog.dismiss()
            }
            catch (ignore: Exception) {

            }
        }

        fun close(dialog: Dialog) {
            try {
                dialog.dismiss()
            }
            catch (ignore: Exception) {

            }
        }
    }

    val binding = DialogBottomLayoutBinding.inflate(layoutInflater)

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