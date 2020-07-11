package appfree.io.locationtracking.modules.location

import android.os.SystemClock
import android.view.contentcapture.ContentCaptureSessionId
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By Ben on 7/10/20
 */
@Entity(tableName = "tbl_track_location")
data class TrackLocation (
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val latitude: Double,
    val longitude: Double,
    val sessionId: String?,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)