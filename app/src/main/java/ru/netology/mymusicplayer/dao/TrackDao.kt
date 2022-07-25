package ru.netology.mymusicplayer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.mymusicplayer.entity.TrackEntity

@Dao
interface TrackDao {
    @Query("SELECT * FROM TrackEntity ORDER BY id ASC")
    fun getTracks(): LiveData<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query(
        """
        UPDATE TrackEntity SET
        liked = CASE WHEN liked THEN 0 ELSE 1 END
        WHERE id = :id
    """
    )
    suspend fun likeById(id: Int)
}
