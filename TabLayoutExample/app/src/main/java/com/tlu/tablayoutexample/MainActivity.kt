package com.tlu.tablayoutexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TabHost
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.tlu.tablayoutexample.R.*
import com.tlu.tablayoutexample.R.id.tab_1
import com.tlu.tablayoutexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabHost
    private var fragment: FrameLayout? = null
    private var list: ArrayList<String> ?=null
    private lateinit var txt_a: EditText

    private lateinit var txt_b: EditText

    private lateinit var btn_cong: Button

    private lateinit var lstView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tabLayout = findViewById(R.id.tabHost)
        tabLayout.setup()
        val tab1 = tabLayout.newTabSpec("Tính tổng")
        tab1.setContent(R.id.tab_1)
        tab1.setIndicator("Tính tổng");
        val tab2 = tabLayout.newTabSpec("Lịch sử")
        tab2.setContent(R.id.tab_2)
        tab2.setIndicator("Lịch sử");
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)

        txt_a = findViewById(R.id.input_A)

        txt_b = findViewById(R.id.input_B)

        btn_cong = findViewById(R.id.btn_cong)
        lstView = findViewById(R.id.list_history)
list= arrayListOf()
        adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list!!)
        lstView.adapter = adapter

        btn_cong.setOnClickListener {
            val cong: Int =
                ("0" + txt_a.text.toString()).toInt() + ("0" + txt_b.text.toString()).toInt()

            list!!.add("${txt_a.text} + ${txt_b.text} = $cong")
//            Toast.makeText(applicationContext, list!!.size.toString(), Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }

}}


