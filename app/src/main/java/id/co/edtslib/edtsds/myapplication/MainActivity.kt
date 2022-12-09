package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.skeleton.RecyclerShimmerView
import id.co.edtslib.edtsds.popup.Popup
import id.co.edtslib.edtsds.popup.PopupDelegate
import id.co.edtslib.edtsds.textfield.TextFieldDelegate
import id.co.edtslib.edtsds.textfield.TextFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aaa = findViewById<TextFieldView>(R.id.aaa)
        aaa.delegate = object : TextFieldDelegate {
            override fun onChanged(input: String?) {
                Toast.makeText(this@MainActivity, "asdsad", Toast.LENGTH_SHORT).show()
            }
        }

    }
}