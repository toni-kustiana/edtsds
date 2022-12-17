package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.stepper2.Stepper2Delegate
import id.co.edtslib.edtsds.stepper2.Stepper2View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stepper = findViewById<Stepper2View>(R.id.stepper)
        stepper.delegate = object : Stepper2Delegate {
            override fun onChangeValue(value: Int) {
                Toast.makeText(this@MainActivity, "$value", Toast.LENGTH_SHORT).show()
            }
        }
    }
}