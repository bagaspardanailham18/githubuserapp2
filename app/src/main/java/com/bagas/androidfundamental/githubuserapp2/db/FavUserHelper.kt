package com.bagas.androidfundamental.githubuserapp2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract.FavUserColumns.Companion.TABLE_NAME
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract.FavUserColumns.Companion.USERNAME
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract.FavUserColumns.Companion._ID


class FavUserHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase = databaseHelper.writableDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavUserHelper? = null
        fun getInstance(context: Context): FavUserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavUserHelper(context)
            }
        private val TAG = FavUserHelper::class.java.simpleName
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun queryByUsername(username: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int =
        database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = $id", null)
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE,"$USERNAME = '$username'", null)
    }

    fun checkUsername(username: String): Boolean {
        val cursor = database.query(
            DATABASE_TABLE,
            null, "$USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null)
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var cursorIndex = 0
            while (cursor.moveToNext()) cursorIndex++
            Log.d(TAG, "username found: $cursorIndex")
        }
        cursor.close()
        return check
    }

}