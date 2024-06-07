package com.tlu.ktraandroid.model


data class DonVi(
    var maDonVi: String = "",
    val ten: String = "",
    val email: String = "",
    val website: String = "",
    val logo: String = "",
    val diaChi: String = "",
    val soDienThoai: String = "",

    var nhanvien: MutableList<NhanVien>? =null,
    val maDonViCha: String = ""
)