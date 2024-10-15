package com.example.ead_ecommerce_app.Session

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "userSessions.db"

        // Table for user sessions
        const val TABLE_SESSIONS = "sessions"
        const val COLUMN_ID = "_id"
        const val COLUMN_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_SESSIONS_TABLE = ("CREATE TABLE " + TABLE_SESSIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " TEXT"
                + ")")
        db?.execSQL(CREATE_SESSIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SESSIONS")
        onCreate(db)
    }
}

