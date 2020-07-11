package appfree.io.locationtracking.modules.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import appfree.io.locationtracking.data.local.TrackSession

/**
 * Created By Ben on 7/11/20
 */
@Dao
interface TrackSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(session: TrackSession)

    @Query("SELECT * FROM tbl_track_session")
    fun getAll(): LiveData<List<TrackSession>>

}