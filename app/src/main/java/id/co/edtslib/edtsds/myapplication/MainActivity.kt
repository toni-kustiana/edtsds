package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.myapplication.databinding.ViewContentSwipeBinding
import id.co.edtslib.edtsds.popup.Popup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ViewContentSwipeBinding.inflate(LayoutInflater.from(this), null, false)
        Popup.showFullScreen(binding.root)

    }
}