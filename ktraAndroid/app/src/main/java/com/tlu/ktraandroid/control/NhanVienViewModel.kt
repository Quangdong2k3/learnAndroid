package com.tlu.ktraandroid.control

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tlu.ktraandroid.helper.FireBaseDataHelper
import com.tlu.ktraandroid.model.DonVi
import com.tlu.ktraandroid.model.NhanVien
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class NhanVienViewModel : ViewModel() {

    private val nvRef = FireBaseDataHelper().getNVRef()
    private val db = FireBaseDataHelper()

    private val _tenNV = MutableStateFlow("")
    val tenNV: StateFlow<String> get() = _tenNV
    private val _maNV = MutableStateFlow("")
    val maNV: StateFlow<String> get() = _maNV
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _soDienThoai = MutableStateFlow("")
    val soDienThoai: StateFlow<String> get() = _soDienThoai


    private val _donVi = MutableStateFlow<DonVi>(DonVi())
    val donVi: StateFlow<DonVi> get() = _donVi

    private val _lstdonVi = MutableStateFlow<List<DonVi>>(emptyList())
    val lstdonVi: StateFlow<List<DonVi>> get() = _lstdonVi

    private val _chucvu = MutableStateFlow("")
    val chucvu: StateFlow<String> get() = _chucvu
    private val _avatar = MutableStateFlow("")
    val avatar: StateFlow<String> get() = _avatar


    private val _dsNhanVien = MutableStateFlow<List<NhanVien>>(emptyList())
    val dsNhanVien: StateFlow<List<NhanVien>> get() = _dsNhanVien


    fun setTenNV(s: String) {
        _tenNV.value = s
    }

    fun setChucVu(s: String) {
        _chucvu.value = s
    }

    fun setEmail(s: String) {
        _email.value = s
    }

    fun setPhone(s: String) {
        _soDienThoai.value = s
    }

    fun setDonVi(dv: DonVi) {
        _donVi.value = dv
    }


    fun getAllNV(callBack: (MutableList<NhanVien>) -> Unit) {
        viewModelScope.launch {
            nvRef.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val l: MutableList<NhanVien> = mutableListOf()
                    for (data in it.result.children) {
                        val e = data.getValue(NhanVien::class.java)
                        l.add(e!!)
                    }
                    callBack(l)
                } else {
                    callBack(mutableListOf())
                }
            }
        }
    }

    fun setAvatar(s: String) {
        _avatar.value = s
    }

    fun setData(nv: NhanVien) {
        _maNV.value = nv.maNhanVien
        _tenNV.value = nv.ten
        _chucvu.value = nv.chucvu
        _email.value = nv.email
        _soDienThoai.value = nv.soDienThoai
        _avatar.value = nv.avatar
        _donVi.value = nv.maDonVi

    }

    //init
    init {
        viewModelScope.launch {
            FireBaseDataHelper().getAllDonVi { donVis, s ->
                _lstdonVi.value = donVis
            }

        }
        viewModelScope.launch {
            getAllNV {
                _dsNhanVien.value = it

            }
        }
    }

    //Action
    fun addOrUpdateNV(idnv: String?, callBack:  (String) -> Unit) {
        viewModelScope.launch {
            val key = idnv ?: nvRef.push().key.toString()
            val nhanvien = NhanVien(
                key,
                _tenNV.value,
                _chucvu.value,
                _email.value,
                _soDienThoai.value,
                _avatar.value,
                _donVi.value ?: DonVi()
            )
            nvRef.child(key).setValue(
                nhanvien

            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        callBack("Add nhân viên thành công")

                        db.getDonVi(_donVi.value.maDonVi) { donVi: DonVi?, s: String? ->
                            if (donVi == null) {
                                callBack("Không tìm thấy đơn vị ")
                                return@getDonVi
                            }
                            if (donVi.nhanvien == null) {
                                donVi.nhanvien = mutableListOf()
                            }
                            val existingDonViIndex =
                                donVi.nhanvien?.indexOfFirst { it.maNhanVien == nhanvien.maNhanVien }
                            if (existingDonViIndex != null && existingDonViIndex != -1) {
                                donVi.nhanvien?.set(existingDonViIndex, nhanvien)
                            } else {
                                donVi.nhanvien!!.add(nhanvien)
                            }


                            // Lưu đơn vị cha đã cập nhật trở lại Firebase
                            db.updateDonVi(donVi) {
                                callBack(it.toString())

                            }
//                            db.getDVRef().child(nhanvien.maDonVi.maDonVi).setValue(donVi)
//                                .addOnCompleteListener {
//                                    if (it.isSuccessful) {
//                                        callBack("Cập nhật thành công đơn vị")
//                                    } else {
//                                        callBack("Cập nhật đơn vị cha thất bại: ${it.exception?.message}")
//                                    }
//                                }
//                                .addOnFailureListener { e ->
//                                    callBack("Cập nhật đơn vị cha thất bại: ${e.message}")
//                                }

                        }
                        getAllNV { _dsNhanVien.value = it }

                    } else {
                        callBack(it.exception?.message.toString())
                    }
                }
        }
    }

    fun getNhanVien(s: String) {
        viewModelScope.launch {
            nvRef.child(s).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val e = snapshot.getValue(NhanVien::class.java)
                    Log.e("data: ", e.toString())
                    setData(e!!)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun deleteNV(nv: NhanVien, callBack: (s: String) -> Unit) {
        viewModelScope.launch {
            nvRef.child(nv.maNhanVien).removeValue()

                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        syncDonViAfterDelete(nv, callBack = {

                            getAllNV { _dsNhanVien.value = it }
                        })
                        callBack("Xóa thành công")

                    } else {
                        callBack("Xóa failed ${it.exception?.message}")
                    }
                }
                .addOnFailureListener { callBack("Xóa thất bại ${it.message}") }
        }
    }

    fun syncDonViAfterDelete(nv: NhanVien, callBack: (s: String?) -> Unit) {
        // Lấy đơn vị cha chứa đơn vị con
        viewModelScope.launch {
            db.getDonVi(nv.maDonVi.maDonVi) { donVi, s ->
                if (donVi == null) {
                    callBack("Không tìm thấy đơn vị cha")
                    return@getDonVi
                }

                if (donVi.nhanvien == null) {
                    donVi.nhanvien = mutableListOf()
                }

                // Xóa đơn vị con khỏi danh sách `donVis` của đơn vị cha
                donVi.nhanvien =
                    donVi.nhanvien?.filter { it.maNhanVien != nv.maNhanVien }?.toMutableList()!!

                // Lưu đơn vị cha đã cập nhật trở lại Firebase
                db.getDVRef().child(nv.maDonVi.maDonVi).setValue(donVi)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            db.updateDonViCha(donVi) {}
                            callBack("Cập nhật thành công đơn vị cha sau khi xóa đơn vị con")
                        } else {
                            callBack("Cập nhật đơn vị cha thất bại: ${it.exception?.message}")
                        }
                    }
                    .addOnFailureListener { e ->
                        callBack("Cập nhật đơn vị cha thất bại: ${e.message}")
                    }
            }
        }
    }

}