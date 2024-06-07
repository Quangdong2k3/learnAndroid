package com.tlu.mycontentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyContentProviderContract {

    public static final String AUTHORITY = "com.example.myprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Notes {
        public static final String TABLE_NAME = "notes";
        public static final String _ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
    }
}
