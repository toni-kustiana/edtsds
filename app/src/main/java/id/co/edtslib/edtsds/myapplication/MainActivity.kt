package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView
import id.co.edtslib.edtsds.bottom.BottomLayout
import id.co.edtslib.edtsds.chips.sliding.ChipItemData
import id.co.edtslib.edtsds.chips.sliding.SlidingChipsView
import id.co.edtslib.edtsds.list.menu.MenuListView
import id.co.edtslib.edtsds.percentagebarview.PercentageBarView
import id.co.edtslib.edtsds.popup.Popup
import id.co.edtslib.edtsds.popup.PopupDelegate
import id.co.edtslib.edtsds.stepper2.Stepper2View
import id.co.edtslib.edtsds.textfield.TextFieldView


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)
        bottomLayout.titleDivider = false
        bottomLayout.titleVisible = false

        val menuListView = bottomLayout.contentView?.findViewById<MenuListView<String>>(R.id.listView)

        val data = mutableListOf<String>()
        for (i in 0 until 100){
            data.add("Item ${i+1}")
        }


        menuListView?.data = data

        val buttonView = findViewById<ButtonView>(R.id.buttonView)
        buttonView.setOnClickListener {
            Toast.makeText(this, "test", Toast.LENGTH_LONG).show()
        }

        bottomLayout.post {
            bottomLayout.collapse()
        }
    }

}