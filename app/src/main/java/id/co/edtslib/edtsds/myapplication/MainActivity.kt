package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.skeleton.MenuSkeletonView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuSkeletonView = findViewById<MenuSkeletonView>(R.id.menuSkeletonView)
        menuSkeletonView.show()
        menuSkeletonView.postDelayed({
            menuSkeletonView.hide()
        }, 5000)

    }
}