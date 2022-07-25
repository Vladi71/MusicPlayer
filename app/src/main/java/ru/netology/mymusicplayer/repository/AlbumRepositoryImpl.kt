package ru.netology.mymusicplayer.repository

import android.media.MediaMetadataRetriever
import androidx.lifecycle.map
import kotlinx.coroutines.*
import ru.netology.mymusicplayer.api.AlbumApi
import ru.netology.mymusicplayer.dao.TrackDao
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.entity.TrackEntity
import ru.netology.mymusicplayer.entity.toDto
import ru.netology.mymusicplayer.entity.toEntity
import ru.netology.mymusicplayer.exceptions.ApiException
import ru.netology.mymusicplayer.exceptions.NetworkException
import ru.netology.mymusicplayer.exceptions.UnknownException
import ru.netology.mymusicplayer.model.TrackUIModel
import java.io.IOException


class AlbumRepositoryImpl(private val dao: TrackDao) : AlbumRepository {

    override val data = dao.getTracks().map(List<TrackEntity>::toDto)

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.service.getAlbum()

            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiException(response.code(), response.message())
            dao.insertTracks(body.tracks.toEntity())

            return body
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override suspend fun likeById(id: Int) {
        try {
            dao.likeById(id)
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }
}
