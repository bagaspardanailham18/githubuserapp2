package com.bagas.androidfundamental.githubuserapp2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract.FavUserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithubuserapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_FAVUSER = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavUserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavUserColumns.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.REPOSITORIES} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.FOLLOWERS} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.FOLLOWING} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.TYPE} TEXT NOT NULL," +
                " ${DatabaseContract.FavUserColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVUSER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}