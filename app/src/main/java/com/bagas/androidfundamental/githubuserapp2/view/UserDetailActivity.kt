package com.bagas.androidfundamental.githubuserapp2.view

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ActivityUserDetailBinding
import com.bagas.androidfundamental.githubuserapp2.db.DatabaseContract
import com.bagas.androidfundamental.githubuserapp2.db.FavUserHelper
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bagas.androidfundamental.githubuserapp2.viewModel.ViewPagerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserDetailBinding

    private lateinit var favUserHelper: FavUserHelper
    private var favorite: FavUser? = null

    private var isChecked = false

    private lateinit var db: FavUser

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.elevation = 0f

        viewPagerConfig()

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE) as FavUser?
        if (favorite != null) {
            setDetailFavorite()
        } else {
            setDetail()
        }

        binding.btnToggleFav.setOnClickListener(this)

    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    private fun setDetailFavorite() {
        val dataFromFavorite = intent.getParcelableExtra<FavUser>(EXTRA_FAVORITE) as FavUser
        val id = dataFromFavorite.id
        val avatar = dataFromFavorite.avatar
        val name = dataFromFavorite.name
        val userName = dataFromFavorite.username
        val company = dataFromFavorite.company
        val location = dataFromFavorite.location.toString()
        val followers = dataFromFavorite.followers
        val following = dataFromFavorite.following
        val repos = dataFromFavorite.repositories

        // Cek isFavorite
        favUserHelper = FavUserHelper.getInstance(this)
        if (favUserHelper.checkUsername(userName.toString())) {
            btn_toggle_fav.isChecked = true
            isChecked = true
        } else {
            btn_toggle_fav.isChecked = false
            isChecked = false
        }

        Glide.with(this)
                .load(avatar)
                .into(binding.tvAvatar)

        binding.tvName.text = name
        binding.tvUsername.text = userName
        binding.tvFollowers.text = followers
        binding.tvFollowing.text = following
        binding.tvRepo.text = repos

        setActionBarTitle("$userName")

        if (location == "null") {
            binding.tvLocation.text = "-"
        } else {
            binding.tvLocation.text = location
        }

        if (company == "null") {
            binding.tvCompany.text = "-"
        } else {
            binding.tvCompany.text = company
        }
    }

    private fun setDetail() {
        val dataDetail = intent.getParcelableExtra<UsersItem>(EXTRA_DETAIL) as UsersItem
        val id = dataDetail.id
        val avatar = dataDetail.avatar
        val name = dataDetail.name
        val userName = dataDetail.username
        val company = dataDetail.company
        val location = dataDetail.location.toString()
        val followers = dataDetail.followers
        val following = dataDetail.following
        val repos = dataDetail.repositories

        // Cek isFavorite
        favUserHelper = FavUserHelper.getInstance(this)
        if (favUserHelper.checkUsername(userName.toString())) {
            btn_toggle_fav.isChecked = true
            isChecked = true
        } else {
            btn_toggle_fav.isChecked = false
            isChecked = false
        }

        Glide.with(this)
                .load(avatar)
                .into(binding.tvAvatar)

        binding.tvName.text = name
        binding.tvUsername.text = userName
        binding.tvFollowers.text = followers
        binding.tvFollowing.text = following
        binding.tvRepo.text = repos

        setActionBarTitle("$userName")

        if (location == "null") {
            binding.tvLocation.text = "-"
        } else {
            binding.tvLocation.text = location
        }

        if (company == "null") {
            binding.tvCompany.text = "-"
        } else {
            binding.tvCompany.text = company
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_toggle_fav -> {
                if (!isChecked) {
                    addUserFavorite()
                    isChecked = !isChecked
                    btn_toggle_fav.isChecked = true
                    checkUserFavorite()
                } else {
                    removeUserFavorite()
                    isChecked = false
                    btn_toggle_fav.isChecked = false
                    checkUserFavorite()
                }
            }
        }
    }


    private fun addUserFavorite() {

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE) as FavUser?
        if (favorite != null) {
            val dataDetail = intent.getParcelableExtra(EXTRA_FAVORITE) as? FavUser
            val avatar = dataDetail?.avatar
            val userName = dataDetail?.username
            val name = dataDetail?.name
            val location = dataDetail?.location
            val repositories = dataDetail?.repositories
            val company = dataDetail?.company
            val followers = dataDetail?.followers
            val following = dataDetail?.following
            val type = dataDetail?.type

            val values = ContentValues().apply {
                put(DatabaseContract.FavUserColumns.AVATAR, avatar)
                put(DatabaseContract.FavUserColumns.USERNAME, userName)
                put(DatabaseContract.FavUserColumns.NAME, name)
                put(DatabaseContract.FavUserColumns.LOCATION, location)
                put(DatabaseContract.FavUserColumns.COMPANY, company)
                put(DatabaseContract.FavUserColumns.REPOSITORIES, repositories)
                put(DatabaseContract.FavUserColumns.FOLLOWERS, followers)
                put(DatabaseContract.FavUserColumns.FOLLOWING, following)
                put(DatabaseContract.FavUserColumns.TYPE, type)
            }
            favUserHelper.insert(values)
        } else {
            val dataDetail = intent.getParcelableExtra(EXTRA_DETAIL) as? UsersItem
            val avatar = dataDetail?.avatar
            val userName = dataDetail?.username
            val name = dataDetail?.name
            val location = dataDetail?.location
            val repositories = dataDetail?.repositories
            val company = dataDetail?.company
            val followers = dataDetail?.followers
            val following = dataDetail?.following
            val type = dataDetail?.type

            val values = ContentValues().apply {
                put(DatabaseContract.FavUserColumns.AVATAR, avatar)
                put(DatabaseContract.FavUserColumns.USERNAME, userName)
                put(DatabaseContract.FavUserColumns.NAME, name)
                put(DatabaseContract.FavUserColumns.LOCATION, location)
                put(DatabaseContract.FavUserColumns.COMPANY, company)
                put(DatabaseContract.FavUserColumns.REPOSITORIES, repositories)
                put(DatabaseContract.FavUserColumns.FOLLOWERS, followers)
                put(DatabaseContract.FavUserColumns.FOLLOWING, following)
                put(DatabaseContract.FavUserColumns.TYPE, type)
            }
            favUserHelper.insert(values)
        }

        Toast.makeText(this, getString(R.string.favorite_add), Toast.LENGTH_SHORT).show()
    }

    private fun removeUserFavorite() {
        favorite = intent.getParcelableExtra(EXTRA_FAVORITE) as FavUser?
        if (favorite != null) {
            val dataDetail = intent.getParcelableExtra(EXTRA_FAVORITE) as? FavUser
            val userName = dataDetail?.username
            favUserHelper = FavUserHelper.getInstance(this)
            favUserHelper.open()
            if (favUserHelper.checkUsername(userName.toString())) favUserHelper.deleteByUsername(userName.toString())
        } else {
            val dataDetail = intent.getParcelableExtra(EXTRA_DETAIL) as? UsersItem
            val userName = dataDetail?.username
            favUserHelper = FavUserHelper.getInstance(this)
            favUserHelper.open()
            if (favUserHelper.checkUsername(userName.toString())) favUserHelper.deleteByUsername(userName.toString())
        }

        Toast.makeText(this, getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show()
    }

    private fun checkUserFavorite() {
        val dataDetail = intent.getParcelableExtra(EXTRA_DETAIL) as? UsersItem
        val userName = dataDetail?.username
        favUserHelper = FavUserHelper.getInstance(this)
        if (favUserHelper.checkUsername(userName.toString())) {
            isChecked = true
            btn_toggle_fav.isChecked = true
        }
    }

}