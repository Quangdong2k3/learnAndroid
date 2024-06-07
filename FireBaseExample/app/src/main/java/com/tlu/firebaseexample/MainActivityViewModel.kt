package com.tlu.firebaseexample

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    private var db: FirebaseDatabaseHelper? = null
    val username: StateFlow<String> get() = _username
    private val _id = MutableStateFlow("")

    val id: StateFlow<String> get() = _id

    private val _email = MutableStateFlow("")

    val email: StateFlow<String> get() = _email
    private val _listData = MutableLiveData<List<Student>>(emptyList())
    val listData: LiveData<List<Student>> get() = _listData
    private val _classrooms = MutableLiveData<List<Classroom>>()
    val classrooms: LiveData<List<Classroom>> = _classrooms


    init {
        db = FirebaseDatabaseHelper()
        viewModelScope.launch {
            db!!.getAllStudents {
                _listData.value = it
                Log.d("size", it.size.toString())
            }
//            loadClassrooms()

        }
    }

    fun setName(s: String) {
        viewModelScope.launch {
            _username.value = s
        }
    }
    fun setId(s: String) {
        viewModelScope.launch {
            _id.value = s
        }
    }

    fun setEmail(s: String) {
        viewModelScope.launch {
            _email.value = s
        }
    }

    fun addData(context: Context) {
        viewModelScope.launch {
            db?.addStudent(Student("", username.value, email.value)) {
                if (it) {
                    Toast.makeText(context.applicationContext, "Add Success", Toast.LENGTH_SHORT)
                        .show()

                    setEmail("")
                    setName("")
                } else {
                    Toast.makeText(context.applicationContext, "Add Failed", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }
    fun deleteData(context: Context) {
        viewModelScope.launch {
            db?.deleteStudent(id.value) {
                if (it) {
                    Toast.makeText(context.applicationContext, "Delete Success", Toast.LENGTH_SHORT)
                        .show()

                    setEmail("")
                    setName("")
                } else {
                    Toast.makeText(context.applicationContext, "Delete Failed", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }
    fun updateData(context: Context) {
        viewModelScope.launch {
            db?.updateStudent(Student(id.value,username.value,email.value)) {
                if (it) {
                    Toast.makeText(context.applicationContext, "Update Success", Toast.LENGTH_SHORT)
                        .show()

                    setEmail("")
                    setName("")
                } else {
                    Toast.makeText(context.applicationContext, "Update Failed", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }


//    private fun loadClassrooms() {
//        val dbHelper = FirebaseDatabaseHelper()
//        dbHelper.getAllClasses(
//            onSuccess = { classrooms ->
//                _classrooms.value = classrooms
//            },
//            onFailure = { exception ->
//                // Handle error
//            }
//        )
//    }

}