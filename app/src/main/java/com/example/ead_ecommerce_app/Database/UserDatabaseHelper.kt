package com.example.ead_ecommerce_app.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ead_ecommerce_app.Model.User

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_PROFILE_PICTURE = "profile_Picture"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USER_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID TEXT PRIMARY KEY, "
                + "$COLUMN_USERNAME TEXT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_PASSWORD TEXT, "
                + "$COLUMN_ADDRESS TEXT, "
                + "$COLUMN_PROFILE_PICTURE TEXT)") // Added profile picture column
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Method to add a user
    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, user.id)
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password) // You can hash the password before storing
            put(COLUMN_ADDRESS, user.address)
            put(COLUMN_PROFILE_PICTURE, user.profile_Picture)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

//    // Method to get user by email
//    fun getUserByEmail(email: String): User? {
//        val db = this.readableDatabase
//        val cursor = db.query(
//            TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_ADDRESS),
//            "$COLUMN_EMAIL=?", arrayOf(email), null, null, null, null
//        )
//        return if (cursor != null && cursor.moveToFirst()) {
//            val user = User(
//                id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
//                username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
//                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
//                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
//                address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
//            )
//            cursor.close()
//            user
//        } else {
//            null
//        }
//    }

    // Method to get the currently logged-in user (from SQLite)
    fun getLoggedInUser(): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                profile_Picture = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PICTURE)) ?: ""
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    // Method to delete user session (for logout)
    fun deleteUserSession() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    // Method to delete all users (optional for cleanup)
    fun clearAllUsers() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}