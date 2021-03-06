package com.revelatestudio.revelatemountain.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.revelatestudio.revelatemountain.data.remote.HitsItem
import com.revelatestudio.revelatemountain.databinding.ItemLatestMountainWallpaperBinding
import com.revelatestudio.revelatemountain.databinding.ItemPopularMountainWallpaperBinding

import com.revelatestudio.revelatemountain.util.ViewType.LATEST
import com.revelatestudio.revelatemountain.util.ViewType.POPULAR
import com.revelatestudio.revelatemountain.util.getProgressDrawable
import com.revelatestudio.revelatemountain.util.loadImage
import com.revelatestudio.revelatemountain.util.makeToast

typealias OnItemClickListener = (HitsItem) -> Unit

class WallpaperListAdapter(private var viewType: Int, onItemClickListener: OnItemClickListener) : PagingDataAdapter<HitsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            POPULAR -> {
                val binding = ItemPopularMountainWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PopularViewHolder(binding)
            } else -> {
                val binding = ItemLatestMountainWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LatestViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder.itemViewType) {
            POPULAR -> {
                if (item != null) {
                    (holder as PopularViewHolder).bind(item)
                }
            }
            LATEST -> {
                if (item != null) {
                    (holder as LatestViewHolder).bind(item)
                }
            }
        }


    }

    inner class PopularViewHolder(private val binding : ItemPopularMountainWallpaperBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HitsItem) {
            binding.wallpaper.loadImage(item.webformatURL, onSuccess = {}, onFailure = {})
        }
    }

    inner class LatestViewHolder(private val binding: ItemLatestMountainWallpaperBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HitsItem) {
            binding.wallpaper.loadImage(item.webformatURL, onSuccess = {

            }, onFailure = {

            })
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HitsItem>() {
            override fun areItemsTheSame(oldItemResponse: HitsItem, newItemResponse: HitsItem): Boolean {
                return oldItemResponse.id == newItemResponse.id
            }
            override fun areContentsTheSame(oldItemResponse: HitsItem, newItemResponse: HitsItem): Boolean {
                return oldItemResponse == newItemResponse
            }
        }
    }



}