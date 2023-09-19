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

        val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)

        val menus = mutableListOf<String>()
        for (i in 1..100) {
            menus.add("Abah $i")
        }

        val listView = bottomLayout.findViewById<MenuListView<String>>(R.id.listView)
        listView.data = menus.toList()

        bottomLayout.scrollView = listView

        bottomLayout.contentView?.postDelayed({
            bottomLayout.showHalf()
        }, 100)

    }
}