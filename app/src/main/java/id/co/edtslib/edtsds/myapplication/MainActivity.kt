package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.checkboxlist.multiple.MultipleCheckBoxListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name1 = NameData("Abah")

        val name2 = NameData("Abraham")
        name2.disabled = true

        val name3 = NameData("Ade")
        val name4 = NameData("Hezbi")
        val name5 = NameData("Fadil")

        val listView = findViewById<MultipleCheckBoxListView<NameData>>(R.id.listView)
        listView.data = mutableListOf(name1, name2, name3, name4, name5)

    }
}