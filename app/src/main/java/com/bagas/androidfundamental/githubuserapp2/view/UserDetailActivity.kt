package com.bagas.androidfundamental.githubuserapp2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ActivityUserDetailBinding
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bagas.androidfundamental.githubuserapp2.viewModel.ViewPagerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataDetail = intent.getParcelableExtra<UsersItem>(EXTRA_DETAIL) as UsersItem
        val avatar = dataDetail.avatar
        val name = dataDetail.name
        val userName = dataDetail.username
        val company = dataDetail.company
        val location = dataDetail.location
        val followers = dataDetail.followers
        val following = dataDetail.following
        val repos = dataDetail.repositories

        Glide.with(this)
            .load(avatar)
            .into(binding.tvAvatar)

        binding.tvName.text = name
        binding.tvUsername.text = userName
        binding.tvCompany.text = company
        binding.tvLocation.text = location
        binding.tvFollowers.text = followers
        binding.tvFollowing.text = following
        binding.tvRepo.text = repos

        setActionBarTitle(getString(R.string.title_action_bar_detail))

        supportActionBar?.elevation = 0f

        viewPagerConfig()

    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

}