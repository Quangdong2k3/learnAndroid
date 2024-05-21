package com.tlu.managestudent

import android.os.Parcel
import android.os.Parcelable

class LopHoc( id: String,  name: String, sSo: Int) {

     lateinit var maLop: String

    lateinit var tenLop: String
    var numberStudent: Int = 0

    // initializer block
    init {
        maLop = id
        tenLop = name
        numberStudent=sSo
    }

}