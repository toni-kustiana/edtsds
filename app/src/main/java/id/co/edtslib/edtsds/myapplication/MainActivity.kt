package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.shimmer.ShimmerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shimmerView = findViewById<ShimmerView>(R.id.shimmerView)
       // shimmerView.showShimmer()
        shimmerView.postDelayed({
            shimmerView.hideShimmer()
        }, 2000)
    }
}