package com.bagas.androidfundamental.githubuserapp2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bagas.androidfundamental.githubuserapp2.viewModel.FollowingViewModel
import com.bagas.androidfundamental.githubuserapp2.viewModel.ListFollowingAdapter
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowingFragment : Fragment() {

    private lateinit var listFollowingAdapter: ListFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    private var users = arrayListOf<UsersItem>()

    private var favorite: FavUser? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorite = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_FAVORITE) as FavUser?
        if (favorite != null) {
            setFavoriteFollowing()
        } else {
            setFollowing()
        }

        configRecyclerView()
        onClicked()
    }

    private fun setFavoriteFollowing() {
        val intent = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_FAVORITE) as FavUser?
        val getUsername = intent?.username

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowingViewModel::class.java)

        setUserData(getUsername)
        getUserData()
    }

    private fun setFollowing() {
        val intent = activity?.intent?.getParcelableExtra(UserDetailActivity.EXTRA_DETAIL) as UsersItem?
        val getUsername = intent?.username

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowingViewModel::class.java)

        setUserData(getUsername)
        getUserData()
    }

    private fun setUserData(username: String?) {
        if (username != null) {
            followingViewModel.setFollowing(username, requireActivity())
        }
        showLoading(true)
    }

    private fun getUserData() {
        followingViewModel.getFollowing().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                listFollowingAdapter.setData(userItems)
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
        listFollowingAdapter.setOnItemClickCallback(object : ListFollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersItem) {
                moveToDetailActivity(data)
            }
        })
    }

    private fun moveToDetailActivity(data: UsersItem) {
        val intent = Intent(activity, UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.EXTRA_DETAIL, data)
        }
        startActivity(intent)
    }

    private fun configRecyclerView() {
        listFollowingAdapter = ListFollowingAdapter(users)
        listFollowingAdapter.notifyDataSetChanged()

        recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowers.adapter = listFollowingAdapter
        recyclerViewFollowers.setHasFixedSize(true)
    }
}