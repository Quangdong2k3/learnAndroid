package com.tlu.spinnerexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

@SuppressLint("ParcelCreator")
class ImageAdapter(context: Context, resource: Int, objects: MutableList<ImageCustom>) :
    ArrayAdapter<ImageCustom>(context, resource, objects), Parcelable {


    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): ImageCustom? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.image, parent, false)
        }

        val img = getItem(position)

        val img_01 = view?.findViewById<ImageView>(R.id.img_cus)
        if (img_01 != null) {
            img?.let { img_01.setImageResource(it.getImg()) }
        }
        return view!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }


}