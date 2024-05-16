package com.tlu.intentcallsms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var btn_call: Button
    private lateinit var btn_sms: Button
    private lateinit var btnCamera: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()


        btn_call.setOnClickListener(
            ::onClickButton
        )

        btn_sms.setOnClickListener(
            ::onClickButton
        )
        btnCamera.setOnClickListener(
            ::onClickButton
        )
    }

    private fun onClickButton(view: View) {
        when (view.id) {
            R.id.btn_call -> {
                val intentPhone = Intent(this,CallActivity::class.java)
                startActivity(intentPhone)
            }

            R.id.btn_sms -> {
                val intentSMS= Intent(this,SmsActivity::class.java)
                startActivity(intentSMS)
            }
            R.id.btn_camera -> {
                val intentCamera= Intent(this,CameraActivity::class.java)
                startActivity(intentCamera)
            }
        }
    }

    private fun init() {
        btn_call = findViewById(R.id.btn_call)
        btn_sms = findViewById(R.id.btn_sms)
        btnCamera = findViewById(R.id.btn_camera)
    }
}