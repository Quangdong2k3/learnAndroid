package com.tlu.fahrenheit

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var doC: EditText
    private lateinit var doF: EditText

    private lateinit var btn_clear: Button
    private lateinit var btn_convert_to_C: Button
    private lateinit var btn_convert_to_F: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()
        doC = findViewById(R.id.txt_celsius)
        doF = findViewById(R.id.txt_fahrenheit)
        btn_clear = findViewById(R.id.btn_clear)
        btn_convert_to_C = findViewById(R.id.btn_Celsius)
        btn_convert_to_F = findViewById(R.id.btn_Fahrenheit)

        btn_clear.setOnClickListener(::onClickButtonCustom)
        btn_convert_to_C.setOnClickListener(::onClickButtonCustom)
        btn_convert_to_F.setOnClickListener(::onClickButtonCustom)
    }

    private fun onClickButtonCustom(view: View) {
        when (view.id) {
            R.id.btn_clear -> {
                doC.setText("")
                doF.setText("")
                doF.requestFocus()
            }
            //Chuyen sang do C
            R.id.btn_Celsius -> {
                val valueF: Float = ("0" + doF.text.toString()).toFloat()
                Toast.makeText(applicationContext,valueF.toString(),Toast.LENGTH_SHORT).show()
                val valueC: Float = (valueF - 32) * 5 / 9
                Toast.makeText(applicationContext,valueC.toString(),Toast.LENGTH_SHORT).show()
                doC.setText(valueC.toString())
            }

            R.id.btn_Fahrenheit -> {
                val valueC: Float = ("0" + doC.text.toString()).toFloat()
                val valueF: Float = (valueC *9/5) + 32
                doF.setText(valueF.toString())
            }
        }
    }
}