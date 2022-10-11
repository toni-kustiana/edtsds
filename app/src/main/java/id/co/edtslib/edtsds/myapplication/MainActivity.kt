package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.chips.group.GroupChipView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val groupChipView = findViewById<GroupChipView<String>>(R.id.chipGroupView)
        groupChipView.items = mutableListOf("Ade", "Abah", "Fadil", "Hezbi", "Abraham", "Robert", "Viktor", "Fahri", "Jovan", "Kevin")


    }
}