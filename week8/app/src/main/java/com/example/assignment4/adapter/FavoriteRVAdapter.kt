package com.example.assignment4.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment4.databinding.ItemFavoriteBinding
import com.example.assignment4.databinding.ItemMemoBinding
import com.example.assignment4.roomdb.MemoData

class FavoriteRVAdapter (private val favList :List<MemoData>): RecyclerView.Adapter<FavoriteRVAdapter.FavViewHolder>()  {
    inner class FavViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:MemoData)
        {
            with(binding)
            {
                tvTitle.text= data.title
                tvTitle.setTextColor(Color.parseColor(data.color!!))
                if (data.content!!.contains("\n"))
                {
                    Log.e("줄바꿈 존재","true")
                    tvContent.text = data.content?.substring(0,data.content!!.indexOf("\n")-1) +"…"
                }
                else{
                    if(data.content!!.length<15)
                    {
                        tvContent.text = data.content
                    }
                    else{
                        tvContent.text= data.content?.substring(0,15) + "…"
                    }
                }
                tvDay.text=data.day
            }
        }
    }
    interface OnItemClickListener {
        fun onClick(v : View, position:Int)
    }

    fun setItemClickListener(onItemClickListener: FavoriteRVAdapter.OnItemClickListener)
    {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : FavoriteRVAdapter.OnItemClickListener
    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return favList.size
    }
}