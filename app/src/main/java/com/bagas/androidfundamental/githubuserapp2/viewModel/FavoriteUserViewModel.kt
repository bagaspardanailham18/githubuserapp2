package com.bagas.androidfundamental.githubuserapp2.viewModel

import android.content.Context
import android.media.tv.TvContract.Channels.CONTENT_URI
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bagas.androidfundamental.githubuserapp2.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserViewModel : ViewModel() {

    companion object {
        private val TAG = FavoriteUserViewModel::class.java.simpleName
    }

    private val listFavoriteUser = MutableLiveData<ArrayList<FavUser>>()

    fun setUserFavorite(context: Context) {
        val listItems = ArrayList<FavUser>()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val deferredUserFavorites = async(Dispatchers.IO) {
                    val cursor = context.contentResolver.query(
                        CONTENT_URI,
                        null,
                        null,
                        null,
                        null)
                    MappingHelper.mapCursorToArrayList(cursor)
                }
                val userFavorites = deferredUserFavorites.await()
                Log.d(TAG, "$userFavorites")
                listItems.addAll(userFavorites)
                listFavoriteUser.postValue(userFavorites)
                Log.d(TAG, "$listFavoriteUser")
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                Toast.makeText(context, "Error: ${e.message.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getUserFavorite(): LiveData<ArrayList<FavUser>> = listFavoriteUser

}