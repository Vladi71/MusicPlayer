package ru.netology.mymusicplayer.mediaObserver

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MediaLifecycleObserver : LifecycleObserver {
    var player: MediaPlayer? = MediaPlayer()

    fun onPlay() {
        player?.setOnPreparedListener {
            it.start()
        }
        player?.prepareAsync()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        player?.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        player?.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        player?.reset()
    }
}
