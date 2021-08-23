package com.example.coroutinesdemo.coroutines.otpview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_otp.*

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        getOtp()
    }

    private fun getOtp() {
        otp_view.setOtpCompletionListener {
            Log.e("OTP", "getOtp: ", )
        }
    }
}