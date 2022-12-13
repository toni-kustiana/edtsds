package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.textfield.TextFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aaa = findViewById<TextFieldView>(R.id.aaa)
        aaa.text = "TextFieldDelegate TextFieldDelegate TextFieldDelegate TextFieldDelegate"
        //aaa.text = "TextFieldDelegate"

        val bbb = findViewById<TextFieldView>(R.id.bbb)
        bbb.text = "Test"
        bbb.editText?.hint = "Abah Adilah"

    }
}