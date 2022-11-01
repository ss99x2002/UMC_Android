package com.example.assignment4.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.ItemMemoBinding

class MemoRVAdapter(private val dataList:ArrayList<Memo>) : RecyclerView.Adapter<MemoRVAdapter.MemoViewHolder>() {

    //ViewHolder 객체
    inner class MemoViewHolder (val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Memo)
        {
            with(binding)
            {
                tvTitle.text= data.title
                if (data.note!!.contains("\n"))
                {
                    Log.e("줄바꿈 존재","true")
                    tvContent.text = data.note?.substring(0,data.note!!.indexOf("\n")-1) +"…"
                }
                else{
                    if(data.note!!.length<15)
                    {
                        tvContent.text = data.note
                    }

                    else{
                        tvContent.text= data.note?.substring(0,15) + "…"
                    }
                }
                tvDay.text=data.day
            }
        }
    }

    //리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v : View, position:Int)
    }

    //외부에서 클릭 시 발생 할 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener)
    {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
       val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MemoViewHolder(binding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.binding.icDelete.setOnClickListener {
            dataList.remove(dataList[position])
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }


    //표현할 Item 총 개수
    override fun getItemCount(): Int {
       return dataList.size
    }

}