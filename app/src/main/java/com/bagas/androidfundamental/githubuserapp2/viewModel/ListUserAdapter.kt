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

class ListUserAdapter(private val mData: ArrayList<UsersItem>) : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UsersItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserAdapter.UserViewHolder {
        val mView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.UserViewHolder, position: Int) {
        val data = mData[position]

        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(55,55))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(holder.tvAvatar)

        holder.tvUsername.text = data.username
        holder.type.text = data.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(mData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        var tvAvatar = binding.avatar
        var tvUsername = binding.tvUsername
        var type = binding.tvType
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersItem)
    }
}