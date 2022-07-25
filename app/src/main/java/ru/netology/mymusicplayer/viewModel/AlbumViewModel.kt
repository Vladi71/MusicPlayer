package ru.netology.mymusicplayer.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.mymusicplayer.db.AppDb
import ru.netology.mymusicplayer.model.AlbumModel
import ru.netology.mymusicplayer.model.TrackModel
import ru.netology.mymusicplayer.model.TrackUIModel
import ru.netology.mymusicplayer.repository.AlbumRepository
import ru.netology.mymusicplayer.repository.AlbumRepositoryImpl

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository = AlbumRepositoryImpl(
        AppDb.getInstance(context = application).trackDao()
    )

    val data: LiveData<TrackModel> = repository.data.map(::TrackModel)

    private val _album = MutableLiveData(AlbumModel())
    val album: LiveData<AlbumModel>
        get() = _album

    init {
        loadAlbum()
    }

    private fun loadAlbum() = viewModelScope.launch {
        try {
            _album.value = AlbumModel(loading = true)
            _album.value = AlbumModel(repository.getAlbum())
        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }

    fun likeById(id: Int) = viewModelScope.launch {
        try {
            repository.likeById(id)
        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }
}