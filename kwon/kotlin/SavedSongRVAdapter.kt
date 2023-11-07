package com.example.umc_5th_1week

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_5th_1week.databinding.ItemSongBinding

class SavedSongRVAdapter():  RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener:MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song: Song){
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            val coverImg = song.coverImg
            if (coverImg != null) {
                binding.itemSongImgIv.setImageResource(coverImg)
            } else {
                binding.itemSongImgIv.setImageResource(R.drawable.img_album_exp)
            }
        }
    }
}