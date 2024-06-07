package com.tlu.ktraandroid.model

data class NhanVien(
    val maNhanVien: String = "",
    val ten: String = "",
    val chucvu: String = "",

    val email: String = "",

    val soDienThoai: String = "",
    val avatar: String = "",
    val maDonVi: DonVi = DonVi(),
)

val listChucVus = listOf(
    "Kiểm toán", "Công tố viên", "Chủ Tich", "Trưởng phòng", "Nhân viên", "Giám đốc"

)