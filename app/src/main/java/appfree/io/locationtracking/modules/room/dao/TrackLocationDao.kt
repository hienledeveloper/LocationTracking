package appfree.io.locationtracking.modules.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import appfree.io.locationtracking.modules.location.TrackLocation

/**
 * Created By Ben on 7/10/20
 */
@Dao
interface TrackLocationDao {

    @Insert
    fun insert(trackLocation: TrackLocation)

    @Query("SELECT * FROM tbl_track_location")
    fun getAll(): LiveData<List<TrackLocation>>

}