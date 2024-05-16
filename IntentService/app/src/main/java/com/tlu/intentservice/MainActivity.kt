package com.tlu.intentservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btn_play:ImageView
    private  lateinit var btn_stop:ImageView
    private var isFlag:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_play = findViewById(R.id.btn_play)
        btn_play.setOnClickListener(View.OnClickListener {
            var intent1:Intent = Intent(this,MyService::class.java)
            startService(intent1)
            isFlag = if(isFlag){
                btn_play.setImageResource(R.drawable.baseline_pause_circle_24)
                false
            }else{
                btn_play.setImageResource(R.drawable.baseline_play_circle_filled_24)
                true
            }
        })
        btn_stop = findViewById(R.id.btn_stop)
        btn_stop.setOnClickListener(View.OnClickListener {
            var intent2:Intent = Intent(this,MyService::class.java)
           stopService(intent2)
            btn_play.setImageResource(R.drawable.baseline_play_circle_filled_24)
            isFlag = true
        })
    }
}