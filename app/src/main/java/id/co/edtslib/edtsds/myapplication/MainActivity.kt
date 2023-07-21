package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.bottom.BottomLayout
import id.co.edtslib.edtsds.bottom.BottomLayoutDelegate
import id.co.edtslib.edtsds.list.menu.MenuDelegate
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
        bottomLayout.halfSnap = true
        bottomLayout.delegate = object : BottomLayoutDelegate {
            override fun onDismiss() {
                TODO("Not yet implemented")
            }

            override fun onCollapse() {
                Log.d("abah", "abah collapse")
                bottomLayout.isVisible = false
            }

            override fun onExpand() {
                Log.d("abah", "abah expand")
            }
        }

        val listView = bottomLayout.contentView?.findViewById<MenuListView<String>>(R.id.listView)
        listView?.data = list.toList()
        listView?.delegate = object : MenuDelegate<String> {
            override fun onSelected(t: String) {
                Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()
            }
        }

        bottomLayout.scrollView = listView

        bottomLayout.post {
            bottomLayout.showHalf()
        }

    }

}