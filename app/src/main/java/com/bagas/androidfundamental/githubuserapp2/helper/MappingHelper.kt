package com.bagas.androidfundamental.githubuserapp2.helper

import android.database.Cursor
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser

object MappingHelper {
    fun mapCursorToArrayList(favUserCursor: Cursor?): ArrayList<FavUser> {
        val favUserList = ArrayList<FavUser>()
        favUserCursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.LOCATION))
                val repositories = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.REPOSITORIES))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.FOLLOWING))
                val type = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.TYPE))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.AVATAR))
                favUserList.add(FavUser(avatar, username, name, company, location, repositories, followers, following, id, type))
            }
        }
        return favUserList
    }

    fun mapCursorToObject(favUserCursor: Cursor?): FavUser {
        var favUser = FavUser()
        favUserCursor?.apply {
            moveToFirst()
            val id = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.USERNAME))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.NAME))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.COMPANY))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.LOCATION))
            val repositories = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.REPOSITORIES))
            val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.FOLLOWERS))
            val following = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.FOLLOWING))
            val type = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.TYPE))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavUserColumns.AVATAR))
            favUser = FavUser(avatar, username, name, company, location, repositories, followers, following, id, type)
        }
        return favUser
    }
}