package com.maks.courseproject.ui.fragments.location_screens.location_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maks.courseproject.databinding.ItemCharactersBinding
import com.maks.courseproject.domain.model.characters.CharactersResultDTO

class DetailLocCharactersAdapter :
    ListAdapter<CharactersResultDTO, DetailLocCharactersAdapter.CharactersViewHolder>(Comparator()) {

    var onItemClickListener: ((CharactersResultDTO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharactersViewHolder(parent)

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(getItem(position)!!)

    inner class CharactersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        ItemCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding = ItemCharactersBinding.bind(itemView)

        fun bind(item: CharactersResultDTO) = with(binding) {
            with(item) {
                Glide.with(itemView)
                    .load(image)
                    .transition(DrawableTransitionOptions.withCrossFade(700))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(itemCharacterImage)

                itemCharacterName.text = name
                itemCharacterSpecies.text = species
                itemCharacterStatus.text = status
                itemCharacterGender.text = gender

                itemView.setOnClickListener{
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }
}

class Comparator :
    DiffUtil.ItemCallback<CharactersResultDTO>() {
    override fun areItemsTheSame(
        oldItem: CharactersResultDTO,
        newItem: CharactersResultDTO
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersResultDTO,
        newItem: CharactersResultDTO
    ): Boolean {
        return oldItem == newItem
    }
}