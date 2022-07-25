package ru.netology.mymusicplayer.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.mymusicplayer.dto.Track

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val file: String,
    val liked: Boolean = false,
    val duration: Long
) {
    fun toDto(): Track {
        return Track(
            id,
            file,
            liked,
            duration
        )
    }
}

fun List<TrackEntity>.toDto(): List<Track> = map(TrackEntity::toDto)
fun List<Track>.toEntity(): List<TrackEntity> = map(Track::toEntity)
fun Track.toEntity(): TrackEntity = TrackEntity(
    id = id,
    file = file,
    liked = liked,
    duration = duration
)

