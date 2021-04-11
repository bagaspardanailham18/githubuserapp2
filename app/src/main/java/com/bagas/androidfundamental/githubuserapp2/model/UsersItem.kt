package com.bagas.androidfundamental.githubuserapp2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersItem(
    var avatar: String? = null,
    var username: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var repositories: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var id: Int? = null,
    var type: String? = null
) : Parcelable