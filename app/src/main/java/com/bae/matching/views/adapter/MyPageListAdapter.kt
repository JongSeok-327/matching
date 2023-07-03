package com.bae.matching.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bae.matching.databinding.ItemMyPageBinding
import com.bae.matching.utils.MyPageItem

class MyPageListAdapter(private val callback: (MyPageItem) -> Unit)
    :RecyclerView.Adapter<MyPageListAdapter.MyPageViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val binding = ItemMyPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        val item = MyPageItem.values()[position]
        holder.binding.apply {
            textTitle.setText(item.stringRes)
            root.setOnClickListener {
                callback.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = MyPageItem.values().size

    inner class MyPageViewHolder(val binding: ItemMyPageBinding):
        RecyclerView.ViewHolder(binding.root)
}