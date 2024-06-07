package com.tlu.ktraandroid.model
data class DonViCha(
    val maDVCha: String = "",
    val tenDV: String = "",
    var donVis: MutableList<DonVi>? = mutableListOf()
)