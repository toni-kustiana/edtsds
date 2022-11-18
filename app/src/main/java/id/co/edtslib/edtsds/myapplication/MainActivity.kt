package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.textfield.date.DateFieldView
import id.co.edtslib.edtsds.textfield.sex.SexFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateFieldView = findViewById<DateFieldView>(R.id.dateFieldView)
        dateFieldView.error = "test"

        val sexFieldView = findViewById<SexFieldView>(R.id.sexFieldView)
        sexFieldView.error = "test"


    }
}