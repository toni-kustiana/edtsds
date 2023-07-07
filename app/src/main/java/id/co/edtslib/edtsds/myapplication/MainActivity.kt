package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.bottom.BottomLayout
import id.co.edtslib.edtsds.list.menu.MenuListView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<String>()
        for (i in 1..1000) {
            list.add("$i")
        }

        val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)
        val listView = bottomLayout.contentView?.findViewById<MenuListView<String>>(R.id.listView)
        listView?.data = list.toList()

        bottomLayout.onInitialDraw()


    }

}