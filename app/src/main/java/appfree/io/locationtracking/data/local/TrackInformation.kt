package appfree.io.locationtracking.data.local

/**
 * Created By Ben on 7/10/20
 */
data class TrackInformation (
    val distance: Float
) {
    val distanceMeter: String
    get() = "$distance meter"
}