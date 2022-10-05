package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.myapplication.databinding.ViewContentSwipeBinding
import id.co.edtslib.edtsds.myapplication.databinding.ViewTitleSwipeBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = LayoutInflater.from(this)
        val bindingContent: ViewContentSwipeBinding =
            ViewContentSwipeBinding.inflate(inflater, null, false)

        val bindingTitle: ViewTitleSwipeBinding =
            ViewTitleSwipeBinding.inflate(inflater, null, false)


        //BottomLayoutDialog.showSwipeTray(this, "Test", bindingContent.root)
        BottomLayoutDialog.showSwipeTray(this, "Test", bindingContent.root)
/*
        val params = WindowManager.LayoutParams()
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP
        params.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        val alertBox = AlertBoxView(this)
        alertBox.message = "test"
        alertBox.type = AlertBoxView.AlertType.Error
        alertBox.dismissible = false

        val mWM = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWM.addView(alertBox, params)

        alertBox.setOnClickListener {
            mWM.removeView(alertBox)
        }

        val exampleView = findViewById<TextView>(R.id.exampleView)
        exampleView.setOnClickListener {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        }*/

    }
}