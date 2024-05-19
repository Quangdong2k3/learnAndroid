package com.tlu.spinnerexample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.GridView
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {

    //init
    private lateinit var txtTitle: TextView
    private lateinit var cbCategory: Spinner

    private val list: ArrayList<String> = arrayListOf(
        "Hàng Điện tử", "Hàng Hóa Chất",
        "Hàng Gia dụng", "Hàng xây dựng"
    )
    private val list_img: ArrayList<Int> = arrayListOf(
       R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5
    )
    private val countries: Array<String> = arrayOf(
        "India", "Australia", "West indies", "indonesia", "Indiana",
        "South Africa", "England", "Bangladesh", "Srilanka", "singapore"
    )
    val img_cus:ArrayList<ImageCustom> = arrayListOf()

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
        txtTitle = findViewById(R.id.txt_title)
        cbCategory = findViewById(R.id.cb_category)
        cbCategory.onItemSelectedListener = this


        val adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            list
        )

        val adapter_img = ImageAdapter(this,R.layout.image,img_cus)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.select_dialog_singlechoice_material)
        cbCategory.adapter = adapter
        val adapter1 = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            countries
        )
        val a: AutoCompleteTextView = findViewById(R.id.autoText)
        a.threshold = 1
        a.setAdapter(adapter1)

        val fewRandomSuggestedText = arrayOf(
            "a", "ant", "apple", "asp", "android", "animation", "adobe",
            "chrome", "chromium", "firefox", "freeware", "fedora"
        )
        val fewTags = arrayOf(
            "Java", "JavaScript", "Spring", "Java EE", "Java 8", "Java 9", "Java 10",
            "MongoDB", "MarshMallow", "NoSQL", "NativeApp", "SQL", "SQLite"
        )
        val randomArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                fewRandomSuggestedText
            )
        val b: MultiAutoCompleteTextView = findViewById(R.id.multi)
        b.setAdapter(randomArrayAdapter)
        b.threshold = 1


//
//        val tagArrayAdapter: ArrayAdapter<String?> =
//            ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, fewTags)
//        b.setAdapter(tagArrayAdapter)
//        b.setThreshold(2)


        b.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        for (i in list_img)  img_cus.add(ImageCustom(i))

        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = adapter_img

        gridView.onItemClickListener = this
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        txtTitle.text = list[position]


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("img", img_cus[position].getImg().toString())
        startActivity(intent)
        Toast.makeText(this, img_cus[position].getImg().toString(),Toast.LENGTH_SHORT).show()
    }
}