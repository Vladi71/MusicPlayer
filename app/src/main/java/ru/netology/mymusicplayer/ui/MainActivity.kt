package ru.netology.mymusicplayer.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.mymusicplayer.BuildConfig.BASE_URL
import ru.netology.mymusicplayer.adapter.AlbumAdapter
import ru.netology.mymusicplayer.adapter.OnInteractionListener
import ru.netology.mymusicplayer.databinding.ActivityMainBinding
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.mediaObserver.MediaLifecycleObserver
import ru.netology.mymusicplayer.viewModel.AlbumViewModel
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.mymusicplayer.model.TrackUIModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private val mediaObserver = MediaLifecycleObserver()
    private var playList: List<Track> = emptyList()
    private var currentIndex = 0
    private val currentTrackId = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(mediaObserver)
        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: TrackUIModel) {
                currentTrackId.value = if (track.id == currentTrackId.value) {
                    null
                } else {
                    track.id
                }
                playerController(BASE_URL + track.file)
            }

            override fun onLike(track: TrackUIModel) {
                viewModel.likeById(track.id)
            }
        })
        binding.rvSongList.adapter = adapter
        binding.rvSongList.addItemDecoration(
            DividerItemDecoration(binding.rvSongList.context, DividerItemDecoration.VERTICAL),
        )
        viewModel.album.observe(this) { state ->
            binding.progressBarView.isVisible = state.loading
            binding.apply {
                tvAlbumName.text = state.album.title
                tvAuthorName.text = state.album.artist
                tvGenreLabel.text = state.album.genre
            }
        }
        MediatorLiveData<Pair<List<Track>, Int?>>().also { mediator ->
            mediator.addSource(viewModel.data) {
                mediator.value = it.tracks to currentTrackId.value
            }
            mediator.addSource(currentTrackId) {
                mediator.value = viewModel.data.value?.tracks.orEmpty() to it
            }
        }.observe(this) { (tracks, currentTrack) ->
            playList = tracks

            adapter.submitList(playList.map {
                TrackUIModel.fromDto(it, it.id == currentTrack)
            })
            playList.forEachIndexed { index, track ->
                if (track.id == currentTrack) currentIndex = index
            }
        }
    }

    fun playerController(url: String) {
        val nextListener =
            MediaPlayer.OnCompletionListener {
                mediaObserver.apply {
                    onStop()
                    if (currentIndex <= playList.size) {
                        currentIndex++
                        currentTrackId.value = currentTrackId.value?.inc() ?: 0
                        player?.setDataSource(BASE_URL + playList[currentIndex].file)
                        onPlay()
                    } else {
                        player?.setDataSource(BASE_URL + playList[0].file)
                        onPlay()
                    }
                }
            }
        mediaObserver.apply {
            if (player != null && player?.isPlaying == true) {
                if (currentTrackId.value != null) {
                    onStop()
                    player?.setDataSource(url)
                    onPlay()
                } else {
                    onStop()
                }
            } else if (player != null) {
                player?.reset()
                player?.setOnCompletionListener(nextListener)
                player?.setDataSource(url)
                onPlay()
            }
        }
    }

    fun findTrackInPlayListById(trackUIModel: TrackUIModel): Int {
        var trackIndex = 0
        playList.map {
            if (trackUIModel.id == it.id) trackIndex = playList.indexOf(it)
        }
        return trackIndex
    }

}
