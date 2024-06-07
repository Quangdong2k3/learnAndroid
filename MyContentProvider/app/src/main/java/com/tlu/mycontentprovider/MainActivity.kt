package com.tlu.mycontentprovider

import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get Content Resolver
        val contentResolver: ContentResolver = this.getContentResolver()


// Define projection (columns to retrieve)
        val projection = arrayOf<String>(
            MyContentProviderContract.Notes._ID,
            MyContentProviderContract.Notes.TITLE,
            MyContentProviderContract.Notes.CONTENT
        )


// Optional selection (filter data) and selection arguments
        val selection: String? = null
        val selectionArgs: Array<String>? = null


// Perform query
        val cursor = contentResolver.query(
            MyContentProviderContract.CONTENT_URI.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )


// Iterate over results
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MyContentProviderContract.Notes._ID))
                val title =
                    cursor.getString(cursor.getColumnIndex(MyContentProviderContract.Notes.TITLE))
                val content =
                    cursor.getString(cursor.getColumnIndex(MyContentProviderContract.Notes.CONTENT))
                // Use data from cursor (id, title, content)
            }
            cursor.close()
        }
    }
}
