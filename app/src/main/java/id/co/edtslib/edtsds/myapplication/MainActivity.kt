package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 2)
        calendar.set(Calendar.DATE, 31)

        val calendar1 = Calendar.getInstance()
        calendar1.set(Calendar.YEAR, 2023)
        calendar1.set(Calendar.MONTH, 2)
        calendar1.set(Calendar.DATE, 16)

        //val dateFieldView = findViewById<DateFieldView>(R.id.dateFieldView)
        //dateFieldView.enableFuture = false
        //dateFieldView.minDate = calendar1.time
    }

}