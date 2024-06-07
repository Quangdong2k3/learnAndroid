package com.tlu.ktraandroid.control

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlu.ktraandroid.helper.FireBaseDataHelper
import com.tlu.ktraandroid.model.DonVi
import com.tlu.ktraandroid.model.DonViCha
import com.tlu.ktraandroid.model.NhanVien
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DonViViewModel : ViewModel() {

    private val firebaseDataHelper = FireBaseDataHelper()

    private val donViCha :DVCViewModel= DVCViewModel()

     val tenDVC = MutableStateFlow("")
        fun getNameDVC(){
            firebaseDataHelper.getDonViCha(_madvc.value){
                tenDVC.value=it.tenDV

            }
        }
    private val _maDonVi = MutableStateFlow<String>("")

    val maDonVi: StateFlow<String> get() = _maDonVi
    fun setID(s: String) {
        _maDonVi.value = s
    }

    private val _listDOnvi = MutableStateFlow<List<DonVi>>(emptyList())
    val listDonVi: StateFlow<List<DonVi>> get() = _listDOnvi

    private val _listDonviCha = MutableStateFlow<List<DonViCha>>(emptyList())
    val listDonviCha: StateFlow<List<DonViCha>> get() = _listDonviCha



    private val _ten = MutableStateFlow<String>("")
    val ten: StateFlow<String> get() = _ten

    fun setName(s: String) {
        _ten.value = s
    }

    private val _madvc = MutableStateFlow<String>("")
    val madvc: StateFlow<String> get() = _madvc
    fun setIdDVC(s: String) {
        _madvc.value = s
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email
    fun setEmail(s: String) {
        _email.value = s
    }


    private val _website = MutableStateFlow<String>("")
    val website: StateFlow<String> get() = _website
    fun setWebsite(s: String) {
        _website.value = s
    }

    private val _logo = MutableStateFlow<String>("")
    val logo: StateFlow<String> get() = _logo
    fun setLogo(s: String) {
        _logo.value = s
    }

    private val _diaChi = MutableStateFlow<String>("")
    val diaChi: StateFlow<String> get() = _diaChi
    fun setAddress(s: String) {
        _diaChi.value = s
    }

    private val _soDienThoai = MutableStateFlow("")
    val soDienThoai: StateFlow<String> get() = _soDienThoai
    fun setPhone(s: String) {
        _soDienThoai.value = s
    }

    private val _getAllDonVis = MutableStateFlow<List<DonVi>>(emptyList())
    val getAllDonVis: StateFlow<List<DonVi>> get() = _getAllDonVis

    init {
        viewModelScope.launch {
            firebaseDataHelper.getAllDonVi { donVis, s ->
                _listDOnvi.value = donVis
                Log.d("MEssage Ex: ", s)
            }
            firebaseDataHelper.getAllDVC {
                _listDonviCha.value=it
            }
        }
    }

    fun addDonVi(donVi: DonVi, context: Context) {
        Log.d("MA Cha",donVi.maDonViCha)
        firebaseDataHelper.addDonVi(donVi) { success, message ->
            if (success) {
                if (donVi.maDonViCha != "") {
                    firebaseDataHelper.updateDonViCha(donVi) {
                        Toast.makeText(
                            context,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                setData(DonVi())
                Toast.makeText(
                    context,
                    "DonVi added successfully",
                    Toast.LENGTH_SHORT
                ).show()


            } else {
                Toast.makeText(
                    context,
                    Exception(message).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun getDonVi(id: String, context: Context) {
        viewModelScope.launch {
            firebaseDataHelper.getDonVi(id) { donVi: DonVi?, s: String? ->
                if (donVi != null) {
                    Log.d("Donvi", donVi.toString())
                    Toast.makeText(
                        context,
                        "DonVi get successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    setData(donVi)
                } else {
                    Toast.makeText(
                        context,
                        Exception(s).toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
    fun deleteDV(donvi:DonVi,c:Context){
        viewModelScope.launch {
            firebaseDataHelper.deleteDV(
                donvi
            ){
                Toast.makeText(
                    c,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun searchDV(s:String){
        viewModelScope.launch {
            if(s==""){
                firebaseDataHelper.getAllDonVi{data,mess->
                    _listDOnvi.value=data
                }
            }else{
                firebaseDataHelper.searchDV(s){list,message->
                    _listDOnvi.value=list
                    Log.d("Search Don vi",list.toString())

                }
            }

        }
    }
    fun updateDV(c: Context) {
        viewModelScope.launch {
            firebaseDataHelper.updateDonVi(
                DonVi(
                    _maDonVi.value,
                    _ten.value,
                    _email.value,
                    _website.value,
                    _logo.value,
                    _diaChi.value,
                    _soDienThoai.value,
                    _listNhanVien.value.toMutableList()
                    ,_madvc.value
                )
            ) {
                Toast.makeText(
                    c,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val _listNhanVien = MutableStateFlow<List<NhanVien>>(emptyList())
    val listNhanVien: StateFlow<List<NhanVien>> get() = _listNhanVien
    private fun setData(dv: DonVi) {
        _ten.value = dv.ten
        _email.value = dv.email
        _logo.value = dv.logo
        _maDonVi.value = dv.maDonVi
        _website.value = dv.website
        _diaChi.value = dv.diaChi
        _soDienThoai.value = dv.soDienThoai
        _listNhanVien.value = dv.nhanvien?: mutableListOf()
        _madvc.value = dv.maDonViCha

    }
}