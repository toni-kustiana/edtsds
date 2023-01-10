package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.myapplication.databinding.ViewContentSwipeBinding
import id.co.edtslib.edtsds.stepper2.Stepper2Delegate
import id.co.edtslib.edtsds.stepper2.Stepper2View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stepperView = findViewById<Stepper2View>(R.id.stepperView)
        stepperView.delegate = object : Stepper2Delegate {
            override fun onValueChanged(view: Stepper2View, value: Int) {
                Toast.makeText(this@MainActivity, "$value", Toast.LENGTH_SHORT).show()
            }

            override fun onReachMax(view: Stepper2View) {
            }

            override fun onReachMin(view: Stepper2View) {
            }
        }
    }
}