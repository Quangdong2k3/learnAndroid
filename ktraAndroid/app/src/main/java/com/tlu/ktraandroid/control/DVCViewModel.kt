package com.tlu.ktraandroid.control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlu.ktraandroid.helper.FireBaseDataHelper
import com.tlu.ktraandroid.model.DonViCha
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DVCViewModel : ViewModel() {

    private val db = FireBaseDataHelper()


    private val _listDVC = MutableStateFlow<List<DonViCha>>(emptyList())
    val listDVC :StateFlow<List<DonViCha>> = _listDVC

    init{
        viewModelScope.launch {
            db.getAllDVC {
                _listDVC.value=it
            }
        }
    }




}