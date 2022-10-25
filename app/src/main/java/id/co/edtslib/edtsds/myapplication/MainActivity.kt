package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.list.checkmenu.RadioButtonListDelegate
import id.co.edtslib.edtsds.list.radiobuttonlist.RadioButtonListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<RadioButtonListView<Employee>>(R.id.listView)
        listView.data = listOf(Employee("Ade"), Employee("Abah"),
            Employee("Fadil"), Employee("Hezbi"), Employee("Abraham"))
        listView.selectedIndex = 4
        listView.delegate = object : RadioButtonListDelegate<Employee> {
            override fun onSelected(t: Employee) {
                Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
}