package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView
import id.co.edtslib.edtsds.chips.sliding.ChipItemData
import id.co.edtslib.edtsds.chips.sliding.SlidingChipsView
import id.co.edtslib.edtsds.percentagebarview.PercentageBarView
import id.co.edtslib.edtsds.popup.Popup
import id.co.edtslib.edtsds.popup.PopupDelegate
import id.co.edtslib.edtsds.stepper2.Stepper2View
import id.co.edtslib.edtsds.textfield.TextFieldView
import id.co.edtslib.edtsds.textfield.date.DateFieldView
import java.util.Date


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val dateFieldView = findViewById<DateFieldView>(R.id.dateFieldView)
        dateFieldView.date = Date()
    }

}