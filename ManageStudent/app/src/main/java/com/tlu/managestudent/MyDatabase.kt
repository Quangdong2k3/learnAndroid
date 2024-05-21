package com.tlu.managestudent

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class MyDatabase(
    context: Context?,
    factory: SQLiteDatabase.CursorFactory?


) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    val createTableClass: String =
        "CREATE TABLE $TABLE_NAME ($ID_COL TEXT PRIMARY KEY NOT NULL, $NAME_COl TEXT, $SISO_COL INTEGER)"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableClass)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(c: LopHoc) {
        val contentValues = ContentValues()
        contentValues.put(ID_COL, c.maLop)
        contentValues.put(NAME_COl, c.tenLop)
        contentValues.put(SISO_COL, c.numberStudent)


        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, contentValues)


        db.close()
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<LopHoc> {

        val list: ArrayList<LopHoc> = arrayListOf()

        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    LopHoc(
                        cursor.getString(cursor.getColumnIndex(ID_COL)).toString(),
                        cursor.getString(
                            cursor.getColumnIndex(
                                NAME_COl
                            )
                        ),
                        cursor.getString(cursor.getColumnIndex(SISO_COL)).toInt()
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun deleteLopHoc(id: String): Int {
        var isFlag = 0


        val db = this.writableDatabase
        try {
            isFlag = db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id))

        } catch (e: Exception) {
            e.printStackTrace()
        }
        db.close()
        return isFlag
    }
    fun editLopHoc(c:LopHoc): Int {
        var isFlag = 0


        val db = this.writableDatabase
        try {
            val contentValues = ContentValues()
            contentValues.put(ID_COL, c.maLop)
            contentValues.put(NAME_COl, c.tenLop)
            contentValues.put(SISO_COL, c.numberStudent)
            isFlag = db.update(TABLE_NAME,contentValues, "$ID_COL=?", arrayOf(c.maLop))

        } catch (e: Exception) {
            e.printStackTrace()
        }
        db.close()
        return isFlag
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "LopHoc.db"

        // below is the variable for database version
        private val DATABASE_VERSION = 2

        // below is the variable for table name
        val TABLE_NAME = "lophoc_table"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "tenLop"

        // below is the variable for age column
        val SISO_COL = "numberStudent"
    }
}