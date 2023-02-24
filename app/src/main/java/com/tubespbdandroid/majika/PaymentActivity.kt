package com.tubespbdandroid.majika

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.tubespbdandroid.majika.data.StringQR
import com.tubespbdandroid.majika.retrofit.payment.PaymentClient
import kotlinx.android.synthetic.main.activity_payment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val actionbar = supportActionBar
        actionbar!!.title = "Pembayaran"
        actionbar.setDisplayHomeAsUpEnabled(true)

        setupPermissions()
        val codeScanner = CodeScanner(this, scn)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    val paymentCall = PaymentClient.service.getPaymentStatus(it.text)

                    paymentCall.enqueue(object: Callback<StringQR> {
                        override fun onResponse(call: Call<StringQR>, response: Response<StringQR>) {
                            if (response.body()!!.status == "SUCCESS") {
                                tv_text.text = "Pembayaran Berhasil"
                                Handler().postDelayed({
                                    startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
                                }, 5000)
                            }
                            if (response.body()!!.status == "FAILED") {
                                tv_text.text = "Pembayaran Gagal, Coba Lagi"
                            }
                        }

                        override fun onFailure(call: Call<StringQR>, t: Throwable) {
                            println(t.message)
                        }
                    })
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "codeScanner: ${it.message}")
                }
            }

            scn.setOnClickListener {
                codeScanner.startPreview()
            }

        }

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val CAMERA_REQ = 101
    }
}