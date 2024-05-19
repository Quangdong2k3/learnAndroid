package com.tlu.listviewexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class ListView_Calendar : AppCompatActivity() {
    private lateinit var lst_work: ArrayList<String>
    private lateinit var name: EditText
    private lateinit var hour: EditText
    private lateinit var minute: EditText
    private lateinit var btn: Button
    private lateinit var txt_date: TextView
    private lateinit var lstView: ListView
    private lateinit var adapter: ArrayAdapter<String>





    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view_calendar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }





        lst_work= arrayListOf()
        name = findViewById(R.id.txt_work)
        hour = findViewById(R.id.txt_hour)
        minute = findViewById(R.id.txt_minute)
        lstView = findViewById(R.id.lst_view_work)
        btn = findViewById(R.id.btn_add_work)
        txt_date = findViewById(R.id.txt_date)

        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val text = date.format(formatter)
        txt_date.text="Hôm nay: ${text}"



        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("work_list", null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        lst_work = gson.fromJson(json, type) ?: ArrayList()

        // Khởi tạo adapter và cài đặt cho ListView
        if(lst_work.size>0){
            adapter = ArrayAdapter(this, R.layout.item_view, lst_work)
            lstView.adapter = adapter

            adapter.notifyDataSetChanged()
        }
        val sharedPreferences1 = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        btn.setOnClickListener {
            lst_work.add("+ ${name.text.toString().capitalize()} - ${hour.text} : ${minute.text}");
            adapter = ArrayAdapter(this, R.layout.item_view, lst_work)
            lstView.adapter=adapter
            adapter.notifyDataSetChanged()

            // Lưu danh sách mới vào SharedPreferences
            val editor = sharedPreferences1.edit()
            val gson1 = Gson()
            val json1 = gson1.toJson(lst_work)
            editor.putString("work_list", json1)
            editor.apply()


        }
    }
}

