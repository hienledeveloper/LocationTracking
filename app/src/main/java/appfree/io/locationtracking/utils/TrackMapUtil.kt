package appfree.io.locationtracking.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import androidx.core.app.ActivityCompat
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.view.extensions.writeBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import java.io.File
import kotlin.math.abs

/**
 * Created By Ben on 7/15/20
 */
object TrackMapUtil {

    // check location permission
    private fun hasPermission(context: Context?): Boolean {
        if (context == null) return false
        return !(ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                )
    }

    @SuppressLint("MissingPermission")
    fun setUp(context: Context?, googleMap: GoogleMap) {
        if (context == null) return
        googleMap.setMaxZoomPreference(20f)
        googleMap.setMinZoomPreference(12f)
        if (hasPermission(context))
            googleMap.isMyLocationEnabled = true
    }

    // view this location on map UI
    fun moveCameraTo(googleMap: GoogleMap, location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
    }

    // draw line on Map & approximate between locations
    fun drawPolylineToDistance(googleMap: GoogleMap, list: List<TrackLocation>): Float {
        var totalDistance = 0f
        if (list.size > 1) {
            googleMap.clear()

            totalDistance = approximateDistanceOfMeter(list.first(), list.last())
            // draw line on map
            googleMap.addPolyline(PolylineOptions().apply {
                list.forEach { location ->

                    // draw line from 2 locations
                    this.add(LatLng(location.latitude, location.longitude))
                }
            })
        }

        return totalDistance
    }

    private fun approximateDistanceOfMeter(
        fromLocation: TrackLocation,
        toLocation: TrackLocation
    ): Float {
        val from = Location("LocationA")
        from.latitude = fromLocation.latitude
        from.longitude = fromLocation.longitude
        val destination = Location("LocationB")
        destination.latitude = toLocation.latitude
        destination.longitude = toLocation.longitude
        return from.distanceTo(destination)
    }

    // cache map
    fun screenShotMap(context: Context?, googleMap: GoogleMap, sId: String?) {
        googleMap.snapshot { bitmap ->
            File(context?.filesDir, "$sId.png").writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 85)
        }
    }

}