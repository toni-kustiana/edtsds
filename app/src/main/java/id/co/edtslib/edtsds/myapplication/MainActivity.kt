package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView
import id.co.edtslib.edtsds.otpverification.OtpVerificationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data1 = BoardingData(title = "Belanja Mudah", description = "One stop online store yang menyediakan berbagai macam produk dalam satu Aplikasi.",
            image = "ic_onboarding_1")
        val data2 = BoardingData(title = "Beragam Varian Produk", description = "Menyediakan ribuan pilihan produk yang lengkap dengan harga terbaik dari segala kebutuhan.",
            image = "ic_onboarding_1")
        val data3 = BoardingData(title = "Banyak Promonya", description = "Nikmati beragam promo dari indomaret untuk kamu dari promo cashback hingga gratis ongkir.",
            image = "ic_onboarding_1")

        val list = listOf(data1, data2)

        val boardingView = findViewById<BoardingView>(R.id.boardingView)
        boardingView.list = list

    }
}