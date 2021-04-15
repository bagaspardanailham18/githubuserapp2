package com.bagas.androidfundamental.githubuserapp2.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ActivityFavUserBinding
import com.bagas.androidfundamental.githubuserapp2.db.FavUserHelper
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bagas.androidfundamental.githubuserapp2.helper.MappingHelper
import com.bagas.androidfundamental.githubuserapp2.viewModel.FavoriteUserViewModel
import com.bagas.androidfundamental.githubuserapp2.viewModel.ListFavUserAdapter
import kotlinx.coroutines.*

class FavUserActivity : AppCompatActivity() {

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private lateinit var adapter: ListFavUserAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    private lateinit var binding: ActivityFavUserBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
        private var users = ArrayList<FavUser>()
        private val TAG = FavUserActivity::class.java.simpleName
        private val adapter = ListFavUserAdapter(users)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle(getString(R.string.title_activity_favuser))

        binding.rvFavuser.setHasFixedSize(true)

        adapter = ListFavUserAdapter(users)
        binding.rvFavuser.adapter = adapter

        getUserData()
        showRecyclerList()

        onClicked()

    }

    private fun showRecyclerList() {
        binding.rvFavuser.layoutManager = LinearLayoutManager(this)
        adapter = ListFavUserAdapter(users)
        binding.rvFavuser.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun getUserData() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val noteHelper = FavUserHelper.getInstance(this@FavUserActivity)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            noteHelper.close()

            val notes = deferredNotes.await()
            if (notes != null) {
                adapter.setData(notes)
                binding.progressBar.visibility = View.INVISIBLE
                showNotFound(false)
            }
            if (notes.isEmpty()) {
                showNotFound(true)
            }
        }
    }

    private fun showUserFavoriteItems(userFavoriteItems: ArrayList<FavUser>) {
        adapter.listFavUser = userFavoriteItems
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavUser)
    }

    private fun showNotFound(states: Boolean) {
        if (states) {
            binding.notFound.visibility = View.VISIBLE
        } else {
            binding.notFound.visibility = View.GONE
        }
    }

    private fun onClicked() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                adapter.setOnItemClickCallback(object : ListFavUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: FavUser) {
                        moveToDetailActivity(data)
                    }
                })
            }
        }
    }

    private fun moveToDetailActivity(data: FavUser) {
        val intent = Intent(this, UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.EXTRA_FAVORITE, data)
        }
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val noteHelper = FavUserHelper.getInstance(this@FavUserActivity)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            noteHelper.close()

            val notes = deferredNotes.await()
            if (notes != null) {
                adapter.setData(notes)
                binding.progressBar.visibility = View.INVISIBLE
                showNotFound(false)
            }
            if (notes.isEmpty()) {
                showNotFound(true)
            }
        }
    }
}
