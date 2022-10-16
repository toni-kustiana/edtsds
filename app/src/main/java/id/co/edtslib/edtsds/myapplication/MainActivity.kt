package id.co.edtslib.edtsds.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.co.edtslib.edtsds.boarding.BoardingData
import id.co.edtslib.edtsds.boarding.BoardingView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardingData1 = BoardingData(title = "Transaksi Lebih Cepat",
            description = "Kamu cukup menyimpan alamat pengiriman sekali saja untuk mempercepat transaksi berikutnya.",
            image = "shop_run")
        val boardingData2 = BoardingData(title = "Transaksi Lebih Cepat",
            description = "Kamu cukup menyimpan alamat pengiriman sekali saja untuk mempercepat transaksi berikutnya.",
            image = "shop_run")
        val boardingData3 = BoardingData(title = "Transaksi Lebih Cepat",
            description = "Kamu cukup menyimpan alamat pengiriman sekali saja untuk mempercepat transaksi berikutnya.",
            image = "shop_run")

        val boardingView = findViewById<BoardingView>(R.id.boardingView)
        boardingView.list = listOf(boardingData1, boardingData2, boardingData3)

    }
}