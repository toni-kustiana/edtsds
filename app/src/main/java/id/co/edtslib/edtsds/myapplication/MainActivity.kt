package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.textfield.date.DateFieldDelegate
import id.co.edtslib.edtsds.textfield.date.DateFieldView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateView = findViewById<DateFieldView>(R.id.dateView)
        dateView.delegate = object : DateFieldDelegate {
            override fun onDateChanged(date: Date, format: String) {
                Toast.makeText(this@MainActivity, format, Toast.LENGTH_SHORT).show()
            }
        }

    }
}