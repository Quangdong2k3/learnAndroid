package com.tlu.intentcallsms

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CallActivity : AppCompatActivity() {
    private lateinit var btnCallBack: Button
    private lateinit var editCall: EditText
    private lateinit var imgCall: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_call)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCallBack = findViewById(R.id.btn_callBack)
        imgCall = findViewById(R.id.img_call)
        editCall = findViewById(R.id.txt_phone)
        imgCall.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + editCall.text))
            // Yêu cầu người dùng đồng ý quyền truy cập vào tính năng gọi điện
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    1
                );
                return@setOnClickListener
            }
            startActivity(intentCall);
        }
        btnCallBack.setOnClickListener {
            finish()
        }
    }
}

