package id.co.edtslib.edtsds.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.edtslib.edtsds.ButtonView
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView
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

        val json = "[{\"title\": \"Belanja Mudah\", \"description\": \"One stop online store yang menyediakan berbagai macam produk dalam satu aplikasi\", \"image\": \"ic_onboarding_1\"}, {\"title\": \"Beragam Varian Produk\", \"description\": \"Menyediakan ribuan pilihan produk yang lengkap dengan harga terbaik untuk segala kebutuhan\", \"image\": \"ic_onboarding_2\"}, {\"title\": \"Banyak Promonya\", \"description\": \"Nikmati beragam promo menarik dari Klik Indomaret untuk kamu\", \"image\": \"ic_onboarding_3\"}]"

        val boardingView = findViewById<BoardingView>(R.id.boardingView)
        boardingView.list = Gson().fromJson<List<BoardingData>?>(
            json,
            object : TypeToken<List<BoardingData>?>() {}.type
        )
    }

}