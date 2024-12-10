package com.example.my_android_training.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class sqliteUtils {

    public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "user_theme.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME = "UserTheme";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_USER_ID = "userId";
        private static final String COLUMN_THEME_NAME = "themeName";

        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY " +
                        "AUTOINCREMENT, " + COLUMN_USER_ID + " TEXT NOT NULL, " + COLUMN_THEME_NAME + " TEXT NOT NULL);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }


        public void insertThemes(String userId, List<String> themes) {
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            try {
                for (String theme : themes) {
                    ContentValues values = new ContentValues();
                    values.put("userId", userId);
                    values.put("themeName", theme);
                    db.insert("UserTheme", null, values);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }

        public List<String> queryThemesByUserId(String userId) {
            SQLiteDatabase db = getReadableDatabase();
            List<String> themes = new ArrayList<>();
            Cursor cursor = db.query("UserTheme", new String[]{"themeName"}, "userId = ?",
                    new String[]{userId}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    themes.add(cursor.getString(Math.max(cursor.getColumnIndex("themeName"), 0)));
                }
                cursor.close();
            }
            db.close();
            return themes;
        }

        public void updateTheme(String userId, String oldTheme, String newTheme) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("themeName", newTheme);

            db.update("UserTheme", values, "userId = ? AND themeName = ?", new String[]{userId,
                    oldTheme});

            db.close();
        }

        public boolean isUserIdExists(String userId) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            boolean exists = false;

            try {
                String query = "SELECT COUNT(*) FROM UserTheme WHERE userId = ?";
                cursor = db.rawQuery(query, new String[]{userId});

                if (cursor != null && cursor.moveToFirst()) {
                    exists = cursor.getInt(0) > 0; // 检查查询的结果是否大于 0
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                db.close();
            }

            return exists;
        }
    }

}
