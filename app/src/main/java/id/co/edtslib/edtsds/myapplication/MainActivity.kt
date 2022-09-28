package id.co.edtslib.edtsds.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.SlidingItemLayoutView
import id.co.edtslib.edtsds.list.banner.SlidingBannerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lll = findViewById<SlidingItemLayoutView>(R.id.lll)

        val exampleView = findViewById<SlidingBannerView>(R.id.exampleView)
        exampleView.data = listOf("https://i.postimg.cc/Z0twhtqF/banner1.png",
            "https://i.postimg.cc/Z0twhtqF/banner1.png", "https://i.postimg.cc/Z0twhtqF/banner1.png")

        lll.redraw()

    }
}