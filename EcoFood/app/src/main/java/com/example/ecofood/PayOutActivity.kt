package com.example.ecofood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecofood.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.placeButtonOrder.setOnClickListener {
            val bottomSheetDialog = congratBottomSheetFragMent()
            bottomSheetDialog.show(supportFragmentManager, "CongratBottomSheet")
        }
    }
}
