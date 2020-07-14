package appfree.io.locationtracking.data.local

import kotlin.math.round

/**
 * Created By Ben on 7/10/20
 */
data class TrackInformation (
    var distance: Float = 0f,
    var duration: Long = 0L
) {
    val distanceKm: String
    get() = "${distance} m"

    val speedInKm: String
    get() {
        if (duration > 0 && distance > 0) {
            return "${round(distance / (duration / 1000))} m/s"
        }
        return "0"
    }

    val durationSecond: String
    get() {
        if (duration > 0) {
            return "${(duration/1000).toInt()} s"
        }
        return "0s"
    }

}