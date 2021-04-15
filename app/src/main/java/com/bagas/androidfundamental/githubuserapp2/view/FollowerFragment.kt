package com.bagas.androidfundamental.githubuserapp2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bagas.androidfundamental.githubuserapp2.viewModel.FollowerViewModel
import com.bagas.androidfundamental.githubuserapp2.viewModel.ListFollowerAdapter
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowerFragment : Fragment() {

    private lateinit var listFollowerAdapter: ListFollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    private var users = arrayListOf<UsersItem>()

    private var favorite: FavUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorite = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_FAVORITE) as FavUser?
        if (favorite != null) {
            setFollowerFavorite()
        } else {
            setFollower()
        }

        configRecyclerView()
        onClicked()
    }

    private fun setFollowerFavorite() {
        val intent = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_FAVORITE) as FavUser?
        val getUsername = intent?.username

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowerViewModel::class.java)

        setUserData(getUsername)
        getUserData()
    }

    private fun setFollower() {
        val intent = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_DETAIL) as UsersItem?
        val getUsername = intent?.username

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowerViewModel::class.java)


        setUserData(getUsername)
        getUserData()
    }

    private fun setUserData(username: String?) {
        if (username != null) {
            followerViewModel.setFollowers(username, requireActivity())
        }
        showLoading(true)
    }

    fun getUserData() {
        followerViewModel.getFollowers().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                listFollowerAdapter.setData(userItems)
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
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(states: Boolean) {
        if (states) {
            notFound.visibility = View.VISIBLE
        } else {
            notFound.visibility = View.GONE
        }
    }

    private fun onClicked() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                listFollowerAdapter.setOnItemClickCallback(object : ListFollowerAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UsersItem) {
                        moveToDetailActivity(data)
                    }
                })
            }
        }
    }

    private fun moveToDetailActivity(data: UsersItem) {
        val intent = Intent(activity, UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.EXTRA_DETAIL, data)
        }
        startActivity(intent)
    }

    private fun configRecyclerView() {
        listFollowerAdapter = ListFollowerAdapter(users)
        listFollowerAdapter.notifyDataSetChanged()

        recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowers.adapter = listFollowerAdapter
        recyclerViewFollowers.setHasFixedSize(true)
    }
}