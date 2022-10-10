package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.checkbox.CheckBox
import id.co.edtslib.edtsds.checkbox.CheckBoxDelegate
import id.co.edtslib.edtsds.list.banner.SlidingBannerView
import id.co.edtslib.edtsds.myapplication.databinding.ViewContentSwipeBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val binding: ViewContentSwipeBinding =
            ViewContentSwipeBinding.inflate(LayoutInflater.from(this), null, false)

        BottomLayoutDialog.showSwipeTray(this, "Alamat Pengaraman",
            binding.root)

    }
}