package com.bagas.androidfundamental.githubuserapp2.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ActivityMainBinding
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bagas.androidfundamental.githubuserapp2.viewModel.ListUserAdapter
import com.bagas.androidfundamental.githubuserapp2.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    private var users = arrayListOf<UsersItem>()

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle(getString(R.string.title_action_bar_main))

        binding.rvUser.setHasFixedSize(true)


        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            showSearchData()
        }

        setUserData()

        getUserData()

        showRecyclerList()

        onClicked()
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter(users)
        binding.rvUser.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun onClicked() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UsersItem) {
                        moveToDetailActivity(data)
                    }
                })
            }
        }
    }

    private fun setUserData() {
        mainViewModel.setUser(applicationContext)
        showLoading(true)
    }

    private fun showSearchData() {
        val username = binding.editQuery.text.toString()
        if (username.isEmpty()) return
        showLoading(true)
        mainViewModel.setUserSearch(username, applicationContext)
    }

    private fun getUserData() {
        mainViewModel.getUsers().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
                showNotFound(false)
            }
            if (userItems.isEmpty()) {
                showNotFound(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(states: Boolean) {
        if (states) {
            binding.notFound.visibility = View.VISIBLE
        } else {
            binding.notFound.visibility = View.GONE
        }
    }

    private fun moveToDetailActivity(data: UsersItem) {
        val intent = Intent(this@MainActivity, UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.EXTRA_DETAIL, data)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mintent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mintent)
        }
        return super.onOptionsItemSelected(item)
    }

}