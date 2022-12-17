package com.maks.courseproject.domain.model.characters

import com.maks.courseproject.data.db.entity.CharacterLocate

data class CharactersResultDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: CharacterLocate,
    val name: String,
    val origin: CharacterLocate,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)