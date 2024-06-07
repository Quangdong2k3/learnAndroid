package com.tlu.firebaseexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(context: Context, objects: ArrayList<Student>) :
    ArrayAdapter<Student>(context, 0, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_list_view, parent, false)


        val st = getItem(position)

        val txtView = view.findViewById<TextView>(R.id.textView)
        txtView.setText("$position - ${st?.name} - ${st?.email}")

        return view
    }
}