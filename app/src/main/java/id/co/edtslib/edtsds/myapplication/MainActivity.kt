package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.stepper.StepperDelegate
import id.co.edtslib.edtsds.stepper.StepperView
import id.co.edtslib.edtsds.stepper2.Stepper2Delegate
import id.co.edtslib.edtsds.stepper2.Stepper2View


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val stepperView = findViewById<StepperView>(R.id.stepperView)
        stepperView.canEdit = true

        val stepper2View = findViewById<Stepper2View>(R.id.stepperView2)
        stepper2View.canEdit = true
        stepper2View.max = 6
        stepper2View.delegate = object : Stepper2Delegate {
            override fun onValueChanged(view: Stepper2View, value: Int) {
                Log.d("abah", "abah $value")
            }

            override fun onReachMax(view: Stepper2View) {

            }

            override fun onReachMin(view: Stepper2View) {

            }

            override fun onExpand(view: Stepper2View, value: Int) {

            }

        }

        val buttonView = findViewById<ButtonView>(R.id.buttonView)
        buttonView.setOnClickListener {
            stepper2View.showValueOnly = true
        }

    }

    override fun onBackPressed() {
        val stepperView = findViewById<StepperView>(R.id.stepperView)
        Toast.makeText(this, if (stepperView.isEditing()) "editing" else "lost", Toast.LENGTH_LONG).show()
        //super.onBackPressed()
    }
}