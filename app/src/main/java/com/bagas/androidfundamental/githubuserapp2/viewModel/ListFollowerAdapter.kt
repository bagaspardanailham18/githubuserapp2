package com.bagas.androidfundamental.githubuserapp2.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ItemRowFollowersBinding
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ListFollowerAdapter(private var mDataFollower: ArrayList<UsersItem>) : RecyclerView.Adapter<ListFollowerAdapter.FollowerViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UsersItem>) {
        mDataFollower.clear()
        mDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFollowerAdapter.FollowerViewHolder {
        val mFollowerView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_followers, parent, false)
        return FollowerViewHolder(mFollowerView)
    }

    override fun onBindViewHolder(holder: ListFollowerAdapter.FollowerViewHolder, position: Int) {
        val data = mDataFollower[position]

        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(55,55))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(holder.tvAvatar)

        holder.tvUsername.text = data.username
        holder.type.text = data.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(mDataFollower[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = mDataFollower.size

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFollowersBinding.bind(itemView)

        var tvAvatar = binding.avatar
        var tvUsername = binding.tvUsername
        var type = binding.tvType
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersItem)
    }
}