package com.tlu.intentbroadcast

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private lateinit var btnopen: Button
    private lateinit var edtlink: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.RECEIVE_SMS
//            ) != PackageManager.PERMISSION_GRANTED ||
//            ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.READ_SMS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    android.Manifest.permission.RECEIVE_SMS,
//                    android.Manifest.permission.READ_SMS
//                ),
//                1
//            )
//            return
//        }
        edtlink = findViewById(R.id.txt_link)
        btnopen = findViewById(R.id.btn_go)
        btnopen.setOnClickListener(View.OnClickListener { // TODO Auto-generated method stub
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://" + edtlink.getText().toString())
            )
            startActivity(intent)

        })

    }
}