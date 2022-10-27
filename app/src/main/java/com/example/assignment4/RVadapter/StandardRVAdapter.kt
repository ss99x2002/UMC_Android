package com.example.assignment4.RVadapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment4.data.Memo
import com.example.assignment4.data.Standard
import com.example.assignment4.data.checkedStatus
import com.example.assignment4.databinding.ItemMemoBinding

class StandardRVAdapter(private val dataList :ArrayList<Standard>,private val checkedStatus:ArrayList<checkedStatus>) : RecyclerView.Adapter<StandardRVAdapter.StandardViewHolder> () {
    inner class StandardViewHolder (private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Standard, status:checkedStatus)
        {
            with(binding)
            {
            tvTitle.text = data.title
                tvContent.text = data.note
                tvDay.text = data.day
                sw.isChecked = status.isChecked

                sw.setOnClickListener {
                    status.isChecked = sw.isChecked
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandardViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StandardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandardViewHolder, position: Int) {
       holder.bind(dataList[position],checkedStatus[position])
    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}