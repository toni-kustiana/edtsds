package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.menu.MenuDelegate
import id.co.edtslib.edtsds.list.menu.MenuListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleView = findViewById<MenuListView<String>>(R.id.exampleView)
        exampleView.delegate = object : MenuDelegate<String> {
            override fun onSelected(t: String) {
                Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()
            }
        }
        exampleView.data = listOf("Menu 1", "Menu 2", "Menu 3")

    }
}