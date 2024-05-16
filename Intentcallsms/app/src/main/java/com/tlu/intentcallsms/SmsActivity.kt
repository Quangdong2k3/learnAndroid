package com.tlu.intentcallsms

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SmsActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var imgSMS: ImageButton

    private lateinit var editTextSMS: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnBack = findViewById(R.id.btn_smsBack)
        editTextSMS = findViewById(R.id.txt_sms)
        imgSMS = findViewById(R.id.img_sms)
        imgSMS.setOnClickListener {
            val callIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("smsto:" + editTextSMS.text)
            )
            startActivity(callIntent)
        }
        btnBack.setOnClickListener {
            finish()
        }
    }
}