package id.co.edtslib.edtsds.popup

import android.view.View

interface PopupDelegate {
    fun onClick(popup: Popup, view: View)
}