package com.tlu.ktraandroid.helper

import android.nfc.Tag
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tlu.ktraandroid.model.DonVi
import com.tlu.ktraandroid.model.DonViCha

class FireBaseDataHelper {

    private lateinit var myRef: DatabaseReference
    private lateinit var donviRef: DatabaseReference
    private lateinit var nhanVienRef: DatabaseReference
    private lateinit var storageReference: StorageReference

    init {
        donviRef = FirebaseDatabase.getInstance().getReference("donvi")
        myRef = FirebaseDatabase.getInstance().reference
        nhanVienRef = FirebaseDatabase.getInstance().getReference("nhanvien")
        storageReference = FirebaseStorage.getInstance().getReference("uploads")
        Log.d("Database", myRef.database.toString())
//        addDonViCha()
    }

    fun addDonViCha() {
        val key = "DVC" + myRef.child("donvicha").push().key
        val dvc = DonViCha(key, "DHTL")


        myRef.child("donvicha").child(key).setValue(dvc)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("SUCCESS", "Insert SuccessFully")
                } else {
                    Log.e("error", task.exception?.message.toString())
                }
            }
    }

    fun searchDV(s: String, callBack: (List<DonVi>, String) -> Unit) {
        donviRef.orderByChild("ten").startAt(s).endAt("${s}\uf8ff")

            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<DonVi>()
                    for (document in snapshot.children) {
                        val dv = document.getValue(DonVi::class.java)
                        if (dv?.ten!!.lowercase().contains(s.lowercase())) {
                            list.add(dv!!)

                        }
                        callBack(list, "Thành côcng lấy ds search")

                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(emptyList(), "TData null")

                }
            })
    }

    fun addDonVi(donVi: DonVi, onComplete: (Boolean, String?) -> Unit) {
        val key = myRef.child("donvi").push().key
        if (key == null) {
            onComplete(false, "Could not get key for new DonVi")
            return
        }
        donVi.maDonVi = key
        myRef.child("donvi").child(key).setValue(donVi)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Insert SuccessFully")
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun getAllDonVi(callBack: (MutableList<DonVi>, s: String) -> Unit) {
        myRef.child("donvi")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<DonVi>()
                    for (data in snapshot.children) {
                       try{
                           val donvi = data.getValue(DonVi::class.java)
                           if(donvi!=null){
                               donvi?.let { list.add(it) }

                           }
                       }catch (e:Exception){
                           Log.e("Firebase", "Lỗi khi chuyển đổi dữ liệu: ${e.message}")
                       }
                    }
                    callBack(list, "Thành Công")
                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(emptyList<DonVi>().toMutableList(), error.message)
                }
            })
    }

    fun getDonVi(id: String, callBack: (DonVi?, s: String?) -> Unit) {
        myRef.child("donvi").child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val donVi = snapshot.getValue(DonVi::class.java)
                    callBack(donVi, null)
                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(null, error.message)
                }
            })

    }

    fun updateDonVi(dv: DonVi, callBack: (s: String?) -> Unit) {
        if(dv.maDonVi!=""){
            myRef.child("donvi").child(dv.maDonVi).setValue(dv).addOnCompleteListener {
                if (it.isSuccessful) {
                    updateDonViCha(dv, callBack)
                    callBack("Cập nhật thành công đơn vị")

                } else {
                    callBack("Cập nhật đơn vị thất bại: ${it.exception?.message}")
                }
            }
                .addOnFailureListener { callBack("Cập nhật đơn vị thất bại: ${it.message}") }
        }


    }

    fun getAllDVC(callBack: (List<DonViCha>) -> Unit) {
        myRef.child("donvicha")
            .addValueEventListener(object : ValueEventListener {
                val list = mutableListOf<DonViCha>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        try {
                            val dvc = data.getValue(DonViCha::class.java)
                            dvc?.let { list.add(it) }
                        } catch (e: DatabaseException) {
                            Log.e("Firebase", "Error converting data", e)
                        }
                    }
                    callBack(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(emptyList())
                }
            })
    }

    fun getDonViCha(dvc: String, callBack: (DonViCha) -> Unit) {

        myRef.child("donvicha").child(dvc)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dvc = snapshot.getValue(DonViCha::class.java)
                    callBack(dvc!!)
                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(DonViCha())
                    Log.e("Lỗi", error.message)
                }
            })

    }

    fun updateDonViCha(dv: DonVi, callBack: (s: String?) -> Unit) {
        getDonViCha(dv.maDonViCha) { donViCha ->
            if (donViCha == null) {
                callBack("Không tìm thấy đơn vị cha")
                return@getDonViCha
            }

            if (donViCha.donVis == null) {
                donViCha.donVis = mutableListOf()
            }

            // Tìm đơn vị con trong danh sách `donVis` của đơn vị cha và cập nhật nó
            val existingDonViIndex = donViCha.donVis?.indexOfFirst { it.maDonVi == dv.maDonVi }
            if (existingDonViIndex != null && existingDonViIndex != -1) {
                donViCha.donVis?.set(existingDonViIndex, dv)
            } else {
                donViCha.donVis?.add(dv)
            }

            // Lưu đơn vị cha đã cập nhật trở lại Firebase
            myRef.child("donvicha").child(dv.maDonViCha).setValue(donViCha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        callBack("Cập nhật thành công đơn vị cha")
                    } else {
                        callBack("Cập nhật đơn vị cha thất bại: ${it.exception?.message}")
                    }
                }
                .addOnFailureListener { e ->
                    callBack("Cập nhật đơn vị cha thất bại: ${e.message}")
                }
        }
    }

    fun deleteDV(dv: DonVi, callBack: (s: String) -> Unit) {
        myRef.child("donvi").child(dv.maDonVi).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    syncDonViChaAfterDelete(dv, callBack = {})
                    callBack("Xóa thành công")
                } else {
                    callBack("Xóa failed ${it.exception?.message}")
                }
            }
            .addOnFailureListener { callBack("Xóa thất bại ${it.message}") }
    }

    fun syncDonViChaAfterDelete(dv: DonVi, callBack: (s: String?) -> Unit) {
        // Lấy đơn vị cha chứa đơn vị con
        getDonViCha(dv.maDonViCha) { donViCha ->
            if (donViCha == null) {
                callBack("Không tìm thấy đơn vị cha")
                return@getDonViCha
            }

            if (donViCha.donVis == null) {
                donViCha.donVis = mutableListOf()
            }

            // Xóa đơn vị con khỏi danh sách `donVis` của đơn vị cha
            donViCha.donVis = donViCha.donVis?.filter { it.maDonVi != dv.maDonVi }?.toMutableList()

            // Lưu đơn vị cha đã cập nhật trở lại Firebase
            myRef.child("donvicha").child(dv.maDonViCha).setValue(donViCha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
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

    fun getNVRef(): DatabaseReference {
        return nhanVienRef
    }

    fun getUploadsRef(): StorageReference {
        return storageReference
    }

    fun getDVRef(): DatabaseReference {
        return donviRef
    }
}


