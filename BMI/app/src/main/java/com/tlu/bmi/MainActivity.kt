package com.tlu.bmi

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var chieucao: EditText
    private lateinit var cannang: EditText
    private lateinit var bmi: EditText
    private lateinit var predict: EditText
    private lateinit var btn_bmi: Button
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
        chieucao = findViewById(R.id.input_height)
        cannang = findViewById(R.id.input_weight)
        bmi = findViewById(R.id.input_bmi)
        btn_bmi = findViewById(R.id.btn_bmi)
        predict = findViewById(R.id.input_predict)
        btn_bmi.setOnClickListener {

            try {
                val df = DecimalFormat("#.0")
                val value_ch_cao: Float = chieucao.text.toString().toFloat()
                var value_bmi: Float = 0.0f
                val value_cannang: Float = cannang.text.toString().toFloat()

//                if (value_ch_cao.toString().isEmpty()) {
//                    Toast.makeText(applicationContext, "Chieu cao phai nhập", Toast.LENGTH_SHORT)
//                        .show()
//                } else if (value_cannang.toString().isEmpty()) {
//                    Toast.makeText(applicationContext, "Can nang phai nhập", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
                value_bmi = (value_cannang / Math.pow(value_ch_cao.toDouble(), 2.0)).toFloat()
                Toast.makeText(applicationContext, value_bmi.toString(), Toast.LENGTH_SHORT)
                    .show()
                bmi.setText(df.format(value_bmi))
//                }


                when {
                    value_bmi < 18F -> predict.setText("người gầy")

                    value_bmi in 18F..24F -> {
                        predict.setText("Người bình thường")
                    }

                    value_bmi in 25F..29.9f -> {
                        predict.setText("Người béo phì độ I")
                    }

                    value_bmi in 30F..34.9f -> {
                        predict.setText("Người béo phì độ II")
                    }

                    value_bmi > 35 -> predict.setText("Người béo phì độ III")
                }

            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT)
                    .show()
            }


        }


    }
}