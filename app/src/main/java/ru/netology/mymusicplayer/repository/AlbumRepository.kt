package ru.netology.mymusicplayer.repository

import androidx.lifecycle.LiveData
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.model.TrackUIModel

interface AlbumRepository {
    val data: LiveData<List<Track>>
    suspend fun getAlbum(): Album
    suspend fun likeById(id: Int)
}
