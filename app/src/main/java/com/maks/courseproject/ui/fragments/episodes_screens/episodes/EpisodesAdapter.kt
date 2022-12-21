package com.maks.courseproject.ui.fragments.episodes_screens.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maks.courseproject.databinding.ItemEpisodesBinding
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO

class EpisodesAdapter :
    PagingDataAdapter<EpisodesResultDTO, EpisodesAdapter.LocationsViewHolder>(Comparator()) {

    var onItemClickListener: ((EpisodesResultDTO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationsViewHolder(parent)

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) =
        holder.bind(getItem(position)!!)

    inner class LocationsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding = ItemEpisodesBinding.bind(itemView)

        fun bind(item: EpisodesResultDTO) = with(binding) {
            with(item) {
                itemEpisodesName.text = name
                itemEpisodesNumber.text = episode
                itemEpisodesAirDate.text = air_date

                itemView.setOnClickListener{
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    class Comparator :
        DiffUtil.ItemCallback<EpisodesResultDTO>() {
        override fun areItemsTheSame(
            oldItem: EpisodesResultDTO,
            newItem: EpisodesResultDTO
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EpisodesResultDTO,
            newItem: EpisodesResultDTO
        ): Boolean {
            return oldItem == newItem
        }
    }
}