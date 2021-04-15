package com.bagas.androidfundamental.githubuserapp2.viewModel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.databinding.ItemRowUserBinding
import com.bagas.androidfundamental.githubuserapp2.entity.FavUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ListFavUserAdapter(private val mData: ArrayList<FavUser>) : RecyclerView.Adapter<ListFavUserAdapter.FavUserViewHolder>() {

    private val TAG = ListFavUserAdapter::class.java.simpleName
    private lateinit var onItemClickDetail: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallback) {
        this.onItemClickDetail = onItemClickCallBack
    }

    fun setData(items: ArrayList<FavUser>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
        Log.d(TAG, "$items")
    }

    var listFavUser = ArrayList<FavUser>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFavUserAdapter.FavUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return FavUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListFavUserAdapter.FavUserViewHolder, position: Int) {
        val data = mData[position]

        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(55,55))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(holder.tvAvatar)

        holder.tvUsername.text = data.username
        holder.type.text = data.type

        holder.itemView.setOnClickListener { onItemClickDetail.onItemClicked(mData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = mData.size

    inner class FavUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        var tvAvatar = binding.avatar
        var tvUsername = binding.tvUsername
        var type = binding.tvType
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavUser)
    }
}