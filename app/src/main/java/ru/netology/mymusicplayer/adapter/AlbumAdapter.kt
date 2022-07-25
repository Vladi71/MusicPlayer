package ru.netology.mymusicplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mymusicplayer.R
import ru.netology.mymusicplayer.databinding.SongCardBinding
import ru.netology.mymusicplayer.model.TrackUIModel
import ru.netology.mymusicplayer.utils.Utils


interface OnInteractionListener {
    fun onPlayPause(track: TrackUIModel) {}
    fun onLike(track: TrackUIModel) {}
}

class AlbumAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<TrackUIModel, TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = SongCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }
}

class TrackViewHolder(
    private val binding: SongCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: TrackUIModel) {

        binding.apply {
            tvSongName.text = track.file
            playPauseButtonChange(track)

            mbLike.isChecked = track.liked
            mbPlayPause.setOnClickListener {
                onInteractionListener.onPlayPause(track)
            }
            mbLike.setOnClickListener {
                onInteractionListener.onLike(track)
            }
        }
    }
}

private fun SongCardBinding.playPauseButtonChange(track: TrackUIModel) {

    if (!track.played) {
        mbPlayPause.setIconResource(R.drawable.ic_play_black_and_white)
        mbPlayPause.setIconTintResource(R.color.light_green)
    } else {
        mbPlayPause.setIconResource(R.drawable.ic_pause_button)
        mbPlayPause.setIconTintResource(R.color.red)
    }
}

class TrackDiffCallback : DiffUtil.ItemCallback<TrackUIModel>() {
    override fun areItemsTheSame(oldItem: TrackUIModel, newItem: TrackUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrackUIModel, newItem: TrackUIModel): Boolean {
        return oldItem == newItem
    }
}
