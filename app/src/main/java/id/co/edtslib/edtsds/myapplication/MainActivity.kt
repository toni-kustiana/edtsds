package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.chips.sliding.ChipItemData
import id.co.edtslib.edtsds.list.menu.MenuListView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = mutableListOf<ChipItemData>()
        items.add(ChipItemData("abah adilah"))
        items.add(ChipItemData("habib helsan", id.co.edtslib.edtsds.R.drawable.ic_alert_warning))
        items.add(ChipItemData("hezbi cibinong", "http://www.adilahsoft.com/ic_pin_point_map.png"))

        val listView = findViewById<MenuListView<ChipItemData>>(R.id.listView)
        listView.data = items



    }

}