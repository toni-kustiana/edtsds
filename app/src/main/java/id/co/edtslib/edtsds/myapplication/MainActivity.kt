package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView
import id.co.edtslib.edtsds.list.checkmenu.CheckMenuListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test1 = Test("Ade")
        val test2 = Test("Abah")
        val test3 = Test("Fadil")
        val test4 = Test("Hezbi")
        val test5 = Test("Abraham")

        val exampleView = findViewById<CheckMenuListView<Test>>(R.id.exampleView)
        exampleView.data = listOf(test1, test2, test3, test4, test5)

    }
}