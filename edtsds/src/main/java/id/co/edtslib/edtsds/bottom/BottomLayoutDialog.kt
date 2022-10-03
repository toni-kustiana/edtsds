package id.co.edtslib.edtsds.bottom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import id.co.edtslib.edtsds.databinding.DialogBottomLayoutBinding

open class BottomLayoutDialog(context: Context):
    Dialog(context, com.google.android.material.R.style.Animation_Design_BottomSheetDialog) {
    companion object {
        fun show(context: Context, title: String, contentView: View) {
            val dialog = BottomLayoutDialog(context)
            dialog.binding.bottomLayout.title = title
            dialog.binding.bottomLayout.contentView = contentView
            dialog.binding.bottomLayout.delegate = object : BottomLayoutDelegate {
                override fun onDismiss() {
                    dialog.dismiss()
                }
            }

            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }

        fun showWithNoTray(context: Context, title: String, contentView: View) {
            val dialog = BottomLayoutDialog(context)
            dialog.binding.bottomLayout.title = title
            dialog.binding.bottomLayout.contentView = contentView
            dialog.binding.bottomLayout.tray = false
            dialog.binding.bottomLayout.delegate = object : BottomLayoutDelegate {
                override fun onDismiss() {
                    dialog.dismiss()
                }
            }

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }
    }

    val binding = DialogBottomLayoutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
    }
}