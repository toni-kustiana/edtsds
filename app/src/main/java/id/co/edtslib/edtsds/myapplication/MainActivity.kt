package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.otpview.OtpView
import id.co.edtslib.edtsds.stepper.StepperView
import id.co.edtslib.edtsds.textfield.TextFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stepperView = findViewById<StepperView>(R.id.stepperView)
        stepperView.isEnabled = false

    }
}