package com.salman.gitsy.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salman.gitsy.R
import com.salman.gitsy.databinding.ItemUserListBinding
import com.salman.gitsy.domain.remote.beans.UserBean
import com.salman.gitsy.utility.ItemActionListener

/**
 * Created by Salman Saifi on 17/04/22.
 * Email - zach.salmansaifi@gmail.com
 */

class SearchAdapter : ListAdapter<UserBean, SearchAdapter.ViewHolder>(CallBack()) {

    private var itemActionListener: ItemActionListener<UserBean>? = null

    fun setItemActionListener(itemActionListener: ItemActionListener<UserBean>?) {
        this.itemActionListener = itemActionListener
    }


    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user_list
    }


    internal class CallBack : DiffUtil.ItemCallback<UserBean>() {
        override fun areItemsTheSame(oldItem: UserBean, newItem: UserBean) =
            oldItem.name == newItem.name && oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserBean, newItem: UserBean) =
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
        private val itemActionListener: ItemActionListener<UserBean>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserBean) {
            binding.model = item
            itemActionListener?.let {
                binding.clicker = it
            }
            binding.executePendingBindings()
        }


    }
}