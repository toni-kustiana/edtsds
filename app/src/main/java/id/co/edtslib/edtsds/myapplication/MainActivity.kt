package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.chips.sliding.ChipItemData
import id.co.edtslib.edtsds.chips.sliding.SlidingChipsView


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val slidingStepperView = findViewById<SlidingStepperView>(R.id.slidingStepperView)

        val adapter = slidingStepperView.adapter as StepperAdapter
        for (i in 0..100) {
            adapter.list.add(1)
        }

        adapter.notifyDataSetChanged()

        val slidingChipView = findViewById<SlidingChipsView<ChipItemData>>(R.id.slidingChipView)
        slidingChipView.items = mutableListOf(
            ChipItemData("Testing", "https://cdn-klik-dev.edts.id/assets/mobile/ico-top/Tickets.png", ),
            ChipItemData("Testing", "https://cdn-klik-dev.edts.id/assets/mobile/ico-top/Tickets.png", "https://cdn-klik-dev.edts.id/assets/mobile/ico-top/Tickets-sel.png"),
            ChipItemData("Testing", R.drawable.ic_grocery),
            ChipItemData("Testing", R.drawable.ic_grocery, R.drawable.ic_grocery_selected),
        )
    }

}