package appfree.io.locationtracking.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By Ben on 7/11/20
 */
@Entity(tableName = "tbl_track_session")
data class TrackSession (

    @PrimaryKey
    val uid: String,
    val distance: Float
)