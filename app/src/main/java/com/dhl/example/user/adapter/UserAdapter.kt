package com.dhl.example.user.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.dhl.example.user.vm.UserViewModel
import com.dhl.uimode.R
import com.dhl.uimode.databinding.UserItemBinding

/**
 * Adapter
 * @author dhl
 * 数据交给ViewModel 处理
 */
class UserAdapter(private val userViewModel: UserViewModel) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    class ViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: UserViewModel, position: Int?) {
            binding.position = position
            binding.viewModel = viewModel

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<UserItemBinding>(
                layoutInflater,
                R.layout.user_item,
                parent,
                false
            )
        binding.lifecycleOwner = parent.context as LifecycleOwner

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userViewModel!!, position)
    }

    override fun getItemCount(): Int {
        return userViewModel.userObservableArrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}