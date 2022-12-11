package com.maks.courseproject.ui.fragments.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maks.courseproject.databinding.ItemLocationBinding
import com.maks.courseproject.domain.model.locations.LocationsResultDTO

class LocationsAdapter :
    PagingDataAdapter<LocationsResultDTO, LocationsAdapter.LocationsViewHolder>(Comparator()) {

    var onItemClickListener: ((LocationsResultDTO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationsViewHolder(parent)

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) =
        holder.bind(getItem(position)!!)

    inner class LocationsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding = ItemLocationBinding.bind(itemView)

        fun bind(item: LocationsResultDTO) = with(binding) {
            with(item) {
                itemLocationName.text = name
                itemLocationDimension.text = dimension
                itemLocationType.text = type

                itemView.setOnClickListener{
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    class Comparator :
        DiffUtil.ItemCallback<LocationsResultDTO>() {
        override fun areItemsTheSame(
            oldItem: LocationsResultDTO,
            newItem: LocationsResultDTO
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationsResultDTO,
            newItem: LocationsResultDTO
        ): Boolean {
            return oldItem == newItem
        }
    }
}