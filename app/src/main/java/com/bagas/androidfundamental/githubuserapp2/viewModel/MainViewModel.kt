package com.bagas.androidfundamental.githubuserapp2.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val listUsersNonMutable = ArrayList<UsersItem>()
    private val listUsers = MutableLiveData<ArrayList<UsersItem>>()

    fun setUser(context: Context) {
        val listItems = ArrayList<UsersItem>()

        val url = "https://api.github.com/users"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    //parsing json
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val user = responseObject.getJSONObject(i)
                        val userItems = UsersItem()
                        val username = user.getString("login")
                        userItems.id = user.getInt("id")
                        userItems.username = username
                        userItems.type = user.getString("type")
                        userItems.avatar = user.getString("avatar_url")

                        listItems.add(userItems)

                        setUserDetail(username, context)
                    }

                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setUserSearch(query: String, context: Context) {
        val listItems = ArrayList<UsersItem>()

        val url = "https://api.github.com/search/users?q=$query"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    listUsersNonMutable.clear()
                    //parsing json
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userItems = UsersItem()
                        val username = user.getString("login")
                        userItems.id = user.getInt("id")
                        userItems.username = username
                        userItems.type = user.getString("type")
                        userItems.avatar = user.getString("avatar_url")

                        listItems.add(userItems)

                        setUserDetail(username, context)
                    }

                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setUserDetail(username: String, context: Context) {

        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONObject(result)
                    val userItems = UsersItem()
                    userItems.id = responseObject.getInt("id")
                    userItems.avatar = responseObject.getString("avatar_url")
                    userItems.name = responseObject.getString("name")
                    userItems.username = responseObject.getString("login")
                    userItems.company = responseObject.getString("company")
                    userItems.location = responseObject.getString("location")
                    userItems.repositories = responseObject.getString("public_repos")
                    userItems.followers = responseObject.getString("followers")
                    userItems.following = responseObject.getString("following")
                    userItems.type = responseObject.getString("type")

                    listUsersNonMutable.add(userItems)
                    listUsers.postValue(listUsersNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getUsers() : LiveData<ArrayList<UsersItem>> {
        return listUsers
    }
}