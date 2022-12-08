package com.maks.courseproject.domain.model.characters

data class CharacterDTO(
    val info: Info,
    val results: List<CharactersResultDTO>
)