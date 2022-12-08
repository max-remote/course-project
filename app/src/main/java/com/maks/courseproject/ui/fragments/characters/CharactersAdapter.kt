package com.maks.courseproject.ui.fragments.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.maks.courseproject.databinding.ItemCharactersBinding
import com.maks.courseproject.domain.model.characters.CharactersResultDTO

class CharactersAdapter :
    ListAdapter<CharactersResultDTO, CharactersAdapter.CharactersViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharactersViewHolder(parent)

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class CharactersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        ItemCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding = ItemCharactersBinding.bind(itemView)

        fun bind(item: CharactersResultDTO) = with(binding) {
            with(item) {
                Glide.with(itemView)
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(itemCharacterImage)

                itemCharacterName.text = name
                itemCharacterSpecies.text = species
                itemCharacterStatus.text = status
                itemCharacterGender.text = gender
            }
        }
    }
}

class Comparator :
    DiffUtil.ItemCallback<CharactersResultDTO>() {
    override fun areItemsTheSame(oldItem: CharactersResultDTO, newItem: CharactersResultDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersResultDTO, newItem: CharactersResultDTO): Boolean {
        return oldItem == newItem
    }
}
