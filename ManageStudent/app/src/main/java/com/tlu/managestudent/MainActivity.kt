package com.tlu.managestudent

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var id: EditText
    private lateinit var name: EditText
    private lateinit var siso: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnSelect: Button
    private lateinit var listView: ListView
    private var db: MyDatabase? = null
    private lateinit var adapter: ArrayAdapter<LopHoc>
    private lateinit var list_get_all_data: ArrayList<LopHoc>

    @RequiresApi(Build.VERSION_CODES.P)
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


    @RequiresApi(Build.VERSION_CODES.P)
    private fun init() {
        id = findViewById(R.id.txt_id)
        name = findViewById(R.id.txt_class)
        siso = findViewById(R.id.txt_amount_student)

        btnAdd = findViewById(R.id.btn_insert)

        btnDelete = findViewById(R.id.btn_delete)

        btnSelect = findViewById(R.id.btn_select)

        btnUpdate = findViewById(R.id.btn_update)

        listView = findViewById(R.id.lst_view_data)
        db = MyDatabase(this, null)
        adapter = ArrayAdapter<LopHoc>(this, android.R.layout.simple_list_item_1, list_get_all_data)
        geAll()


        btnAdd.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnUpdate.setOnClickListener(this)
        btnSelect.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun geAll() {
        try {
            list_get_all_data = db?.getAllData()!!

            adapter.notifyDataSetChanged()
            listView.adapter = adapter

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_insert -> {
                try {
                    val c: LopHoc =
                        LopHoc(
                            id.text.toString(),
                            name.text.toString(),
                            siso.text.toString().toInt()
                        )

                    db?.insertData(c)
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_update -> {
                try {
                    val c: LopHoc =
                        LopHoc(
                            id.text.toString(),
                            name.text.toString(),
                            siso.text.toString().toInt()
                        )

                    db?.editLopHoc(c)
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_delete -> {
                try {
                    val c: LopHoc =
                        LopHoc(
                            id.text.toString(),
                            name.text.toString(),
                            siso.text.toString().toInt()
                        )

                    db?.deleteLopHoc(c.maLop)
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_select -> {
                geAll()

            }
        }
    }

}