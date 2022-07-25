package ru.netology.mymusicplayer.utils

import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track

object Utils {

    val emptyAlbum = Album(
        id = 0,
        title = "",
        subtitle = "",
        artist = "",
        published = "",
        genre = "",
        tracks = emptyList()
    )

    val emptyTrack = Track(
        id = 0,
        file = "",
        duration = 0L
    )

    fun formateMillis(milliseconds: Long): String {

        var finalTimerString = ""
        var secondsString = ""

        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        return finalTimerString
    }
}