package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.chips.sliding.ChipItemData
import id.co.edtslib.edtsds.chips.sliding.SlidingChipsView
import id.co.edtslib.edtsds.popup.Popup
import id.co.edtslib.edtsds.popup.PopupDelegate


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

        val btnAsal = findViewById<ButtonView>(R.id.btnAsal)

        btnAsal.setOnClickListener {
            Popup.show(
                activity = this,
                title = "Hapus 2 Barang?",
                message = "Barang yang kamu pilih akan dihapus dari keranjang.",
                positiveButton = "Hapus",
                negativeButton = "Pindahkan ke Favorit",
                positiveClickListener = object : PopupDelegate {
                    override fun onClick(popup: Popup, view: View) {
                        popup.dismiss()

                    }
                },
                negativeClickListener = object : PopupDelegate {
                    override fun onClick(popup: Popup, view: View) {
                        popup.dismiss()

                    }
                },
                orientation = Popup.Orientation.Vertical,
                dismissible = true,
                negativeButtonStyle = ButtonView.ButtonVariant.Alternative
            )
        }
    }

}