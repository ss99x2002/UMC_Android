package com.example.boxoffice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boxoffice.data.Movie
import com.example.boxoffice.data.WeeklyBoxOfficeList
import com.example.boxoffice.databinding.ItemMovieBinding

class MovieRVAdapter(private val dataList:List<WeeklyBoxOfficeList>) :RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>(){
    inner class MovieViewHolder(val binding:ItemMovieBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(data:WeeklyBoxOfficeList)
        {
            with(binding)
            {
                tvRank.text = data.rank
                tvAudi.text= data.audiAcc
                tvOepn.text=data.openDt
                tvTitle.text= data.movieNm
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
}