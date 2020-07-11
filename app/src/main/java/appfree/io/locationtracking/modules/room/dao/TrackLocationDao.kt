package appfree.io.locationtracking.modules.room.dao

import android.view.contentcapture.ContentCaptureSessionId
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

    @Query("SELECT * FROM tbl_track_location WHERE sessionId == :sSessionId")
    fun getAllBySessionId(sSessionId: String?): LiveData<List<TrackLocation>>

}