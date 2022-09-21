package id.co.edtslib.edtsds.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.popup.Popup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Popup.show(this, "Title", "Message",
            "Positive", "Negative", null, null,
            Popup.Orientation.Vertical)
    }
}