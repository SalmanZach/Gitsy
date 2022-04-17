package com.salman.gitsy.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salman.gitsy.R
import com.salman.gitsy.databinding.ItemUserListBinding
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.utility.ItemActionListener

/**
 * Created by Salman Saifi on 17/04/22.
 * Email - zach.salmansaifi@gmail.com
 */

class SearchAdapter : ListAdapter<UserEntity, SearchAdapter.ViewHolder>(CallBack()) {

    private var itemActionListener: ItemActionListener<UserEntity>? = null

    fun setItemActionListener(itemActionListener: ItemActionListener<UserEntity>?) {
        this.itemActionListener = itemActionListener
    }


    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user_list
    }


    internal class CallBack : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.username == newItem.username && oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem == newItem

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemActionListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))


    class ViewHolder(
        private val binding: ItemUserListBinding,
        private val itemActionListener: ItemActionListener<UserEntity>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserEntity) {
            binding.model = item
            itemActionListener?.let {
                binding.clicker = it
            }
            binding.executePendingBindings()
        }


    }
}