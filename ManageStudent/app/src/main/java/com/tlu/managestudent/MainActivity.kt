package com.tlu.managestudent

import android.R.*
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import androidx.core.widget.addTextChangedListener

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
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var list_get_all_data: ArrayList<String>

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            init()
        }


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
        list_get_all_data = arrayListOf()
        listView = findViewById(R.id.lst_view_data)
        db = MyDatabase(this, null)
        adapter =
            ArrayAdapter<String>(applicationContext, layout.simple_list_item_1, list_get_all_data)

        adapter.notifyDataSetChanged()
        getAll()


        btnAdd.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnUpdate.setOnClickListener(this)
        btnSelect.setOnClickListener(this)


        listView.setOnItemClickListener { parent, view, position, id ->

            val s1 = list_get_all_data[position].split("-")[0].trim()
            val s2 = list_get_all_data[position].split("-")[1].trim()
            val s3 = list_get_all_data[position].split("-")[2].trim()


            resetText(s1, s2, s3)


            Log.e("eaaaa", s1.toString())
        }

        this.id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    list_get_all_data.clear()
                    for (c in db?.getDataWithID(s.toString())!!) {
                        list_get_all_data.add("${c.maLop} - ${c.tenLop} - ${c.numberStudent}")
                    }
                    adapter.notifyDataSetChanged()
                    listView.adapter = adapter
                    Log.e("meeee", list_get_all_data.size.toString())

                } catch (e: Exception) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun resetText(s1: String, s2: String, s3: String) {
        this.id.setText(s1)
        this.name.setText(s2)
        this.siso.setText(s3)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getAll() {
        try {
            list_get_all_data.clear()
            for (c in db?.getAllData()!!) {
                list_get_all_data.add("${c.maLop} - ${c.tenLop} - ${c.numberStudent}")
            }
            adapter.notifyDataSetChanged()
            listView.adapter = adapter
            Log.e("meeee", list_get_all_data.size.toString())

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_insert -> {
                val c: LopHoc =
                    LopHoc(
                        id.text.toString(),
                        name.text.toString(),
                        siso.text.toString().toInt()
                    )

                db?.insertData(c)

                getAll()
                resetText("", "", "")

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
                } finally {
                    resetText("", "", "")
                    getAll()
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
                } finally {
                    resetText("", "", "")
                    getAll()
                }
            }

            R.id.btn_select -> {
                getAll()
                resetText("", "", "")
            }
        }
    }

}