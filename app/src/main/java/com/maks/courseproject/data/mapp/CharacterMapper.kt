package com.maks.courseproject.data.mapp

import com.maks.courseproject.data.db.entity.CharacterEntity
import com.maks.courseproject.domain.model.characters.CharactersResultDTO

class CharacterMapper {

    fun mapDTOtoEntity(dto: CharactersResultDTO) = CharacterEntity(
        episode = dto.episode,
        gender = dto.gender,
        id = dto.id,
        image = dto.image,
        name = dto.name,
        species = dto.species,
        status = dto.status,
        type = dto.type,
        created = dto.created,
        location = dto.location,
        origin = dto.origin
    )

}