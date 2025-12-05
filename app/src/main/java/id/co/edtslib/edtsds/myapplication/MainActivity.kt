package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.bottom.BottomLayout
import id.co.edtslib.edtsds.bottom.BottomLayoutDelegate
import id.co.edtslib.edtsds.list.menu.MenuListView


class MainActivity : AppCompatActivity() {
    private var interceptEnabled = true

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)
        val switch = findViewById<SwitchCompat>(R.id.switchIntercept)
        bottomLayout.titleDivider = false
        bottomLayout.titleVisible = false
        bottomLayout.halfSnap = true
        bottomLayout.delegate = object : BottomLayoutDelegate{
            override fun onDismiss() {
                Toast.makeText(this@MainActivity, "Dismissed", Toast.LENGTH_SHORT).show()
            }

            override fun onCollapse() {
                Toast.makeText(this@MainActivity, "Collapsed", Toast.LENGTH_SHORT).show()
            }

            override fun onExpand() {
                Toast.makeText(this@MainActivity, "Expanded", Toast.LENGTH_SHORT).show()
            }

            override fun onInterceptDismiss(): Boolean {
                if (interceptEnabled){
                    showConfirmationDialog()
                    return true
                }
                return false
            }
        }
        switch.isChecked = interceptEnabled
        switch.setOnCheckedChangeListener {_, isChecked ->
            interceptEnabled = isChecked
        }

        val menuListView = bottomLayout.contentView?.findViewById<MenuListView<String>>(R.id.listView)

        val data = mutableListOf<String>()
        for (i in 0 until 100){
            data.add("Item ${i+1}")
        }


        menuListView?.data = data

        val buttonView = findViewById<ButtonView>(R.id.buttonView)
        buttonView.setOnClickListener {
            bottomLayout.showFull()
        }

        bottomLayout.post {
            bottomLayout.collapse()
        }
    }

    private fun showConfirmationDialog() {
        val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)
        AlertDialog.Builder(this)
            .setTitle("Closing Intercepted")
            .setMessage("This closing action is intercepted")
            .setCancelable(false) // Prevent clicking outside this alert
            .setPositiveButton("Continue") { _, _ ->
                bottomLayout.collapse()
            }
            .setNegativeButton("Cancel") { alert, _ ->
                bottomLayout.showFull()
                alert.dismiss()
            }
            .show()
    }

}