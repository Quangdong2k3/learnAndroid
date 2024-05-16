package com.example.tipcalculator

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var amount: EditText
    private lateinit var percent: TextView
    private lateinit var tip: TextView
    private lateinit var total: TextView
    private lateinit var btn_add: Button
    private lateinit var btn_minus: Button
    private lateinit var spinner_split: Spinner
    private lateinit var seek_bar:SeekBar
    private var percentDefault: Float = 0.15f

    // define the SharedPreferences object
    private var savedValues: SharedPreferences? = null
    private var billAmountString: String = ""
    private lateinit var txt_seek_bar : TextView
    @SuppressLint("MissingInflatedId", "SetTextI18n", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        // Gán ID cho các thành phần
        amount = findViewById(R.id.bill_amount)
        percent = findViewById(R.id.value_percent)
        tip = findViewById(R.id.value_tip)
        total = findViewById(R.id.value_total)
        btn_add = findViewById(R.id.btn_add)
        btn_minus = findViewById(R.id.btn_drop)
        spinner_split = findViewById(R.id.spinner_tip)
        spinner_split.onItemSelectedListener = this
seek_bar = findViewById(R.id.seek_bar_percent)
        txt_seek_bar = findViewById(R.id.txt_seek_bar_percent)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this, R.array.spinner_array, android.R.layout.simple_spinner_item
        )
        seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txt_seek_bar.text= progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                txt_seek_bar.text= "${seekBar?.progress}%"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                txt_seek_bar.text= "${seekBar?.progress}%"
            }

        })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_split.adapter = adapter
        // Thêm xử lý sự kiện cho button (nếu cần)
        btn_add.setOnClickListener(::onPercentUpDownButtonClick)

        btn_minus.setOnClickListener(::onPercentUpDownButtonClick)

        amount.setOnEditorActionListener { v, actionId, envent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                calculateDisPlay()
            }
            false;

        }
        // Lấy giá trị của TextView percent và chuyển thành Int
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        percent.text = (String.format("%.0f", percentDefault * 100)) + "%"

    }

    @SuppressLint("SetTextI18n")
    private fun calculateDisPlay() {

        val amount_bill: Float
        amount_bill = if (amount.text.isEmpty()) {
            0.0f
        } else {
            amount.text.toString().toFloat()

        }
        val boa: Float = amount_bill * percentDefault
        tip.text = ""
        total.text = ""
        tip.text = "$" + (String.format("%.2f", boa)).toString()
        total.text = "$" + (String.format("%.2f", boa + amount_bill)).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun onPercentUpDownButtonClick(view: View) {
        when (view.id) {
            R.id.btn_add -> {
                percentDefault += .01f
                percent.text = (String.format("%.0f", percentDefault * 100)) + "%"
                calculateDisPlay()
            }

            R.id.btn_drop -> {
                percentDefault -= .01f
                percent.text = (String.format("%.0f", percentDefault * 100)) + "%"
                calculateDisPlay()
            }
        }
    }


    @SuppressLint("CommitPrefEdits")
    override fun onPause() {
        val editor: Editor = savedValues!!.edit()
        with(editor) {
            putString("billAmountString", billAmountString)
            putFloat("percent_Default", 0.15f)
            commit()
        }
        super.onPause()

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        //Laays Pho Ban
        super.onResume()

        billAmountString = savedValues!!.getString("billAmountString", "").toString()
        percentDefault = savedValues!!.getFloat("percent_Default", 0.15f)
        percent.text = String.format("%.0f", percentDefault * 100) + "%"
        amount.setText(billAmountString)
        calculateDisPlay()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}


