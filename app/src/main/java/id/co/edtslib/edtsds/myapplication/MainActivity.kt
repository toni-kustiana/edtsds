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

        val slidingStepperView = findViewById<SlidingStepperView>(R.id.slidingStepperView)

        val adapter = slidingStepperView.adapter as StepperAdapter
        for (i in 0..100) {
            adapter.list.add(1)
        }

        adapter.notifyDataSetChanged()

    }

}