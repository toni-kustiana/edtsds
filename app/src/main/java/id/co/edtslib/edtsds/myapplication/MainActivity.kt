package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.myapplication.databinding.SkeletonTestBinding
import id.co.edtslib.edtsds.myapplication.databinding.ViewContentSwipeBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ViewContentSwipeBinding.inflate(LayoutInflater.from(this))

        BottomLayoutDialog.showTray(context = this, title = "Dialog 1", contentView = binding.root)


        val binding1 = SkeletonTestBinding.inflate(LayoutInflater.from(this))
        BottomLayoutDialog.showTray(context = this, title = "Dialog 2", contentView = binding1.root,
            isOverlay = false)

    }
}