package id.co.edtslib.edtsds.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleView = findViewById<BoardingView>(R.id.exampleView)

        val item = BoardingData("ic_onboarding_1", "Belanja Mudah",
            "One stop online store yang menyediakan berbagai macam produk dalam satu Aplikasi.")
        val item1 = BoardingData("ic_onboarding_1", "Beragam Varian Produk",
            "Menyediakan ribuan pilihan produk yang lengkap dengan harga terbaik dari segala kebutuhan.")

        val item2 = BoardingData("ic_onboarding_1", "Banyak Promonya",
            "Nikmati beragam promo dari indomaret untuk kamu dari promo cashback hingga gratis ongkir.")

        exampleView.list = listOf(item, item1, item2)

    }
}