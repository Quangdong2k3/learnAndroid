package com.tlu.firebaseexample

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class FirebaseDatabaseHelper {

    private lateinit var databaseReference: DatabaseReference

    init {
        // Khởi tạo FirebaseDatabase instance và DatabaseReference
        val firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setPersistenceEnabled(true)

        databaseReference = firebaseDatabase.getReference("students")


    }

    fun addStudent(student: Student, returnResult: (Boolean) -> Unit) {
        student.id = databaseReference.push().key.toString()
        databaseReference.child(student.id!!).setValue(student)
            .addOnSuccessListener {
                returnResult(true)
            }
            .addOnFailureListener {
                returnResult(false)
            }
    }

    fun deleteStudent(studentId: String, callBack: (Boolean) -> Unit) {
        databaseReference.child(studentId).removeValue().addOnSuccessListener {
            callBack(true)
        }
            .addOnFailureListener { callBack(false) }
    }

    fun getStudent(studentId: String, callback: (Student?) -> Unit) {
        databaseReference.child(studentId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val studentInfo = dataSnapshot.getValue(Student::class.java)
                    callback(studentInfo)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors
                    callback(null)
                }
            })
    }


    fun getDatabaseReference(studentId: String): DatabaseReference {
        return databaseReference
    }

    fun getAllStudents(onDataReceived: (ArrayList<Student>) -> Unit) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val students = ArrayList<Student>()
                for (dataSnapshot in snapshot.children) {
                    val student = dataSnapshot.getValue(Student::class.java)
                    student?.let {


                        students.add(it)
                    }
                }
                onDataReceived(students)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                Log.e("FirebaseDatabaseHelper", "Failed to read data", error.toException())

            }

        })
    }

    fun updateStudent(st: Student, callback: (Boolean) -> Unit) {
        databaseReference.child(st.id.toString()).setValue(st)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)

            }
    }

//    fun getAllClasses(onSuccess: (List<Classroom>) -> Unit, onFailure: (Exception) -> Unit) {
//        classessRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val classess = mutableListOf<Classroom>()
//                for (classSnapshot in snapshot.children) {
//                    val classId = classSnapshot.key ?: continue
//                    val className = classSnapshot.child("className").getValue(String::class.java) ?: ""
//                    val studentSnapshot = classSnapshot.child("students")
//                    val studentlst = mutableListOf<Student>()
//                    for (st in studentSnapshot.children) {
//                        val st = studentSnapshot.getValue(Student::class.java)
//                        st?.let { studentlst.add(it) }
//                    }
//                    classess.add(Classroom(classId,className, studentlst))
//                }
//                onSuccess(classess)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                onFailure(error.toException())
//            }
//        })
//    }

}