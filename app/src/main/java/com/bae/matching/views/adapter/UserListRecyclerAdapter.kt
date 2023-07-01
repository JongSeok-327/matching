package com.bae.matching.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.bae.matching.databinding.ItemUserInfoBinding
import com.bae.matching.model.entities.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListRecyclerAdapter(
    private val callBack: ItemCallback
): ListAdapter<UserResponse, UserListRecyclerAdapter.UserListItemViewHolder>(UserDiffCallBack())
{
    interface ItemCallback {
        fun onClickedItem(userId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = ItemUserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserListItemViewHolder(private val binding: ItemUserInfoBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: UserResponse){
            binding.apply {
                // NickName
                textUserNickName.text = userInfo.nickname
                // Photo
                CoroutineScope(Dispatchers.IO).launch {
                    val imageLoader = ImageLoader(itemView.context)
                    val request = ImageRequest.Builder(itemView.context)
                        .data(userInfo.photo)
                        .build()

                    val drawable = imageLoader.execute(request).drawable
                    withContext(Dispatchers.Main) {
                        imgUserPhoto.setImageDrawable(drawable)
                    }
                }

                // Item Click
                root.setOnClickListener {
                    userInfo.id?.let { callBack.onClickedItem(it)  }
                }
            }
        }
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem == newItem
    }
}