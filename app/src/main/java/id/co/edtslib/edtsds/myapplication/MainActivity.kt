package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.checkbox.CheckBox
import id.co.edtslib.edtsds.checkbox.CheckBoxDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleView = findViewById<CheckBox>(R.id.exampleView)
        exampleView.delegate = object : CheckBoxDelegate {
            override fun onChecked(checked: Boolean) {
                Toast.makeText(this@MainActivity, "$checked", Toast.LENGTH_SHORT).show()
            }
        }

    }
}