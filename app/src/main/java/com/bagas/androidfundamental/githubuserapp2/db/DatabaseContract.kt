package com.bagas.androidfundamental.githubuserapp2.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favuser"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORIES = "repositories"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val TYPE = "type"
            const val AVATAR = "avatar"
        }
    }

}