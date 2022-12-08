package com.maks.courseproject.domain.model.locations

data class LocationDTO(
    val info: Info,
    val results: List<LocationsResultDTO>
)