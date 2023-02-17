package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.textfield.TextFieldDelegate
import id.co.edtslib.edtsds.textfield.TextFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aa = findViewById<TextFieldView>(R.id.etName)
        aa.delegate = object : TextFieldDelegate {
            override fun onChanged(input: String?) {
                Toast.makeText(this@MainActivity, input, Toast.LENGTH_SHORT).show()
            }
        }
    }
}