package ru.netology.mymusicplayer.model

import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.utils.Utils

data class AlbumModel (
    val album: Album = Utils.emptyAlbum,
    val loading: Boolean = false,
    val error: Boolean = false
)