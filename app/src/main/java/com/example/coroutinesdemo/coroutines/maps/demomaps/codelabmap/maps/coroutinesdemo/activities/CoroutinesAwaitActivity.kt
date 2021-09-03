package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.coroutinesdemo.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.coroutinesdemo.utils.CoroutinesUtils
import kotlinx.android.synthetic.main.activity_coroutines_await.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime


class CoroutinesAwaitActivity : AppCompatActivity() {
    companion object {
        const val ALPHABET = "Alphabet string"
    }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_await)
        onclick(this)
        CoroutineScope(Dispatchers.IO).launch {
            val calculateTime = measureTimeMillis {
                val text = async {
                    CoroutinesUtils.getText()
                }
                val alphabet = async {
                    CoroutinesUtils.getAlphabet()
                }

                val divisionResult = async {
                    try {
                        CoroutinesUtils.divideByZero()

                    } catch (e: Exception) {
                        Log.e("TAG", "Exception handled")
                    }
                }

                Log.e("awaitTag", " Division is as follow: ${divisionResult.await()}")
                Log.e("awaitTag", "text is: ${text.await()}")
                Log.e("awaitTag", "alphabet is :${alphabet.await()}")
            }
            Log.e("awaitTag", "Total time: $calculateTime")
            startTaskInParallel()
        }
    }

    private fun onclick(context: Context) {
        btnLaunch.setOnClickListener {
            context.addCustomToast("Customized toast shown", 500)
//            lifecycleScope.launch {
//                while (true) {
//                    Log.e("awaitTag", "Coroutine is still running................")
//                }
//            }
            lifecycleScope.launch {
                Intent(this@CoroutinesAwaitActivity, SecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

        btnShowDialog.setOnClickListener {
        }

    }

    private fun startTaskInParallel() {
        CoroutineScope(Dispatchers.IO).launch {

            val calculateTime2 = measureTimeMillis {
                val intValue = async {
                    CoroutinesUtils.getIntegerValue()
                }
                val floatValue = async {
                    CoroutinesUtils.getFloatValue()
                }
                val combineResults = intValue.await() + floatValue.await()
                Log.e("awaitTag", "startTaskInParallel: $combineResults")
            }
            Log.e("awaitTag", "startTaskInParallel: $calculateTime2")
        }
        main()

    }

    //Extension functions
    fun main() {
        Log.e("extension", "Hi this is extension function".formatString())
    }
    private fun String.formatString(): String {
        return "----------Another String has been used \n $this\n----------"
    }

    private fun Context.addCustomToast(message: CharSequence, duration: Int) {
        Toast.makeText(this@CoroutinesAwaitActivity, message, duration).show()
    }
//    private fun showDialog(title: String) {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_layout)
//        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
//        dialog.show()
//
//    }

}