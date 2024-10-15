package com.example.ead_ecommerce_app.Session

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class SessionManager(context: Context) {
    private val dbHelper = MyDatabaseHelper(context)

    // Save user session
    fun saveSession(userId: String) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(MyDatabaseHelper.COLUMN_USER_ID, userId)
        }

        db.insert(MyDatabaseHelper.TABLE_SESSIONS, null, values)
        //db.close()
    }

    // Retrieve user session
    fun getSession(): Map<String, String>? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${MyDatabaseHelper.TABLE_SESSIONS} LIMIT 1", null)

        return if (cursor.moveToFirst()) {
            val sessionData = mapOf(
                "user_id" to cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_USER_ID))
            )
            cursor.close()
            db.close()
            sessionData
        } else {
            cursor.close()
           // db.close()
            null
        }
    }

    // Clear user session
    fun clearSession() {
        val db = dbHelper.writableDatabase
        db.delete(MyDatabaseHelper.TABLE_SESSIONS, null, null)
        db.close()
    }
}
