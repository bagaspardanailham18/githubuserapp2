package com.bagas.androidfundamental.githubuserapp2.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ItemRowUserBinding
import com.bagas.androidfundamental.githubuserapp2.model.UsersItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ListFollowingAdapter(private var mDataFollowing: ArrayList<UsersItem>) : RecyclerView.Adapter<ListFollowingAdapter.FollowingViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UsersItem>) {
        mDataFollowing.clear()
        mDataFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowingAdapter.FollowingViewHolder {
        val mFollowingView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return FollowingViewHolder(mFollowingView)
    }

    override fun onBindViewHolder(holder: ListFollowingAdapter.FollowingViewHolder, position: Int) {
        val data = mDataFollowing[position]

        Glide.with(holder.itemView.context)
                .load(data.avatar)
                .apply(RequestOptions().override(55,55))
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(holder.tvAvatar)

        holder.tvUsername.text = data.username
        holder.type.text = data.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(mDataFollowing[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = mDataFollowing.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        var tvAvatar = binding.avatar
        var tvUsername = binding.tvUsername
        var type = binding.tvType
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersItem)
    }
}