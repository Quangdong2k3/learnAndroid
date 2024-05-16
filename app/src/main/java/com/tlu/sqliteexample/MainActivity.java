package com.tlu.sqliteexample;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
   EditText title, author, tag;
    ListView lstView;
    private final MySQLiteDB mySQLiteDB = new MySQLiteDB(this);


    private ArrayList<String> lstAdapter = new ArrayList<>();
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        title = findViewById(R.id.txt_title);
        author = findViewById(R.id.txt_author);
        tag = findViewById(R.id.txt_tag);
        add = findViewById(R.id.btn_add);

        lstView = findViewById(R.id.list_item);

        lstAdapter = getAllBook();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstAdapter);
        lstView.setAdapter(adapter);

        add.setOnClickListener(v -> {
            try {
                SQLiteDatabase db = mySQLiteDB.getWritableDatabase();

//                db.execSQL("delete from "+MySQLiteDB.TABLE_BOOK+" Where "+MySQLiteDB.BOOK_ID+"=22");
                ContentValues values = new ContentValues();
                values.put(MySQLiteDB.BOOK_TITLE, title.getText().toString());
                values.put(MySQLiteDB.BOOK_AUTHOR, author.getText().toString());
                values.put(MySQLiteDB.BOOK_TAG, tag.getText().toString());
                db.insert(MySQLiteDB.TABLE_BOOK, null, values);

            } catch (Exception ex) {
                Log.e("error", ex.getMessage());
            }finally {
                lstAdapter.clear();
                lstAdapter.addAll(getAllBook());

                adapter.notifyDataSetChanged();


            }
        });

    }

    @SuppressLint({"DefaultLocale", "Range"})
    private ArrayList<String> getAllBook() {
        ArrayList<String> lstAdapter1 = new ArrayList<>();
        try {
            SQLiteDatabase db = mySQLiteDB.getReadableDatabase();
            @SuppressLint("Recycle") Cursor cursorBooks = db.rawQuery("SELECT * FROM " + MySQLiteDB.TABLE_BOOK, null);
            if (cursorBooks.moveToFirst()) {
                do {
                    // on below line we are adding the data from
                    // cursor to our array list.
                    lstAdapter1.add(new String(
                            String.format("%d - %s %s %s", cursorBooks.getInt(cursorBooks.getColumnIndex(MySQLiteDB.BOOK_ID)), cursorBooks.getString(cursorBooks.getColumnIndex(MySQLiteDB.BOOK_TITLE)) , cursorBooks.getString(cursorBooks.getColumnIndex(MySQLiteDB.BOOK_AUTHOR)),cursorBooks.getString(cursorBooks.getColumnIndex(MySQLiteDB.BOOK_TAG)))

                    ));
                } while (cursorBooks.moveToNext());
                // moving our cursor to next.
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            mySQLiteDB.close();

        }
        return lstAdapter1;
    }
}