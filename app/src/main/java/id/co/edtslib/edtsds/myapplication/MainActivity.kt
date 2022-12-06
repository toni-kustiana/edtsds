package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.skeleton.RecyclerShimmerView
import id.co.edtslib.edtsds.popup.Popup
import id.co.edtslib.edtsds.popup.PopupDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       Popup.show(activity = this, title = "Test", message = "ajajja", negativeButton = "Neg",
        positiveButton = "Pos", negativeClickListener = object : PopupDelegate {
               override fun onClick(popup: Popup, view: View) {
                   popup.dismiss()
               }
           }, positiveClickListener = object : PopupDelegate {
               override fun onClick(popup: Popup, view: View) {
               }
           })

    }
}