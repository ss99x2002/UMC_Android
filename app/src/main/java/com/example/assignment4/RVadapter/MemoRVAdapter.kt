package com.example.assignment4.RVadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.ItemMemoBinding

class MemoRVAdapter(private val dataList:ArrayList<Memo>) : RecyclerView.Adapter<MemoRVAdapter.MemoViewHolder>() {
    //ViewHolder 객체
    inner class MemoViewHolder (private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Memo)
        {
            with(binding)
            {
                tvTitle.text= data.title
                if(data.note!!.length<15)
                {
                    tvContent.text = data.note
                }
                else{
                    tvContent.text= data.note?.substring(0,15) + "…"
                }
                tvDay.text=data.day
            }

        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
       val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MemoViewHolder(binding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.itemView.setOnClickListener {
            dataList.remove(dataList[position])
            notifyDataSetChanged()
        }
    }


    //표현할 Item 총 개수
    override fun getItemCount(): Int {
       return dataList.size
    }

}