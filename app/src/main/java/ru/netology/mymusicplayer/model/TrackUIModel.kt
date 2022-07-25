package ru.netology.mymusicplayer.model

import ru.netology.mymusicplayer.dto.Track

data class TrackUIModel(
    val id: Int,
    val file: String,
    val liked: Boolean = false,
    val duration: Long,
    val played: Boolean = false,
) {
    companion object {
        fun fromDto(dto: Track, played: Boolean) =
            with(dto) {
                TrackUIModel(
                    id = id,
                    file = file,
                    liked = liked,
                    duration = duration,
                    played = played,
                )
            }
    }
}