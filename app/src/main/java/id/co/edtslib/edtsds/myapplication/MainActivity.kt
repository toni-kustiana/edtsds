package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.textfield.SexFieldDelegate
import id.co.edtslib.edtsds.textfield.SexFieldView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sexView = findViewById<SexFieldView>(R.id.sexView)
        sexView.delegate = object : SexFieldDelegate {
            override fun onSelected(sex: SexFieldView.Sex) {
                Toast.makeText(this@MainActivity, sex.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
}