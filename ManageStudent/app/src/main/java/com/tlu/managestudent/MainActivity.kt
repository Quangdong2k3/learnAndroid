package com.tlu.managestudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var id  :EditText
    private  lateinit var name:EditText
    private  lateinit var siso:EditText
    private lateinit var btnAdd:Button
    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button
    private lateinit var btnSelect:Button
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


        
    }


    private fun init(){
        id = findViewById(R.id.txt_id)
        name = findViewById(R.id.txt_class)
        siso = findViewById(R.id.txt_amount_student)

        btnAdd=findViewById(R.id.btn_insert)

        btnDelete = findViewById(R.id.btn_delete)

        btnSelect = findViewById(R.id.btn_select)

        btnUpdate=findViewById(R.id.btn_update)
    }
}