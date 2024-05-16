package com.tlu.th2

import android.annotation.SuppressLint
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

class MainActivity : AppCompatActivity() {

    private lateinit var so_a: EditText

    private lateinit var so_b: EditText



    private lateinit var so_kq: EditText

    private lateinit var btn_tong: Button
    private lateinit var btn_tich: Button
    private lateinit var btn_hieu: Button
    private lateinit var btn_thuong: Button



    private   var input_a : Int =0
    private  var input_b : Int =0
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
        so_a = findViewById(R.id.input_A_EditText)
        so_b = findViewById(R.id.input_B_EditText)
        so_kq = findViewById(R.id.input_KQ_EditText)

        btn_tong = findViewById(R.id.btn_add)
        btn_hieu = findViewById(R.id.btn_minus)
        btn_tich = findViewById(R.id.btn_tich)
        btn_thuong = findViewById(R.id.btn_thuong)

        so_kq.setText("0")
        btn_tong.setOnClickListener(listener)

        btn_tich.setOnClickListener(listener)
        btn_thuong.setOnClickListener(listener)
        btn_hieu.setOnClickListener(listener)

    }

    @SuppressLint("SetTextI18n")
    private val listener = View.OnClickListener { view ->


        when (view.id) {

            R.id.btn_add -> {
//                Toast.makeText(applicationContext, "Add", Toast.LENGTH_SHORT).show()
                getValueEditText()
                so_kq.setText((input_a+input_b).toString())
            }

            R.id.btn_minus -> {
                getValueEditText()
                so_kq.setText((input_a-input_b).toString())
//                Toast.makeText(applicationContext, "Hieu", Toast.LENGTH_SHORT).show()
            }

            R.id.btn_tich -> {
                getValueEditText()
                so_kq.setText((input_a*input_b).toString())
//                Toast.makeText(applicationContext, "Tich", Toast.LENGTH_SHORT).show()
            }

            R.id.btn_thuong -> {
                try {
                    getValueEditText()
                    var chia :Float = 0.0f
                    chia = (input_a.toFloat()/input_b.toFloat())
                    so_kq.setText(chia.toString())
                }catch (e:Exception){
                    Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
                }

//                Toast.makeText(applicationContext, "Thuong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getValueEditText(){
        input_a = if (so_a.getText().toString().trim() == ""){
            0

        }else{
            so_a.text.toString().toInt()

        }


        input_b = if(so_b.text.toString().trim()==""){
            0
        }else{
            so_b.getText().toString().toInt()
        }
    }

}