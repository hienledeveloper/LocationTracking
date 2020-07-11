package appfree.io.locationtracking.modules.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import appfree.io.locationtracking.data.local.TrackInformation
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.view.extensions.writeBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import java.io.File
import kotlin.math.abs

/**
 * Created By Ben on 7/10/20
 */
class TrackMapManager {

    private lateinit var mMap: GoogleMap
    private var currentLocation: TrackLocation? = null
    private var mTempLatitude = 0.0
    private var mTempLongitude = 0.0

    fun setUp(context: Context?, googleMap: GoogleMap) {
        if (context == null) return
        mMap = googleMap
        mMap.setMaxZoomPreference(20f)
        mMap.setMinZoomPreference(12f)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true

    }

    fun moveCameraTo(trackLocation: TrackLocation) {
        if (currentLocation == null) {
            val sydney = LatLng(trackLocation.latitude, trackLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        currentLocation = trackLocation
    }

    fun drawPolyline(list: List<TrackLocation>) {
        mMap.clear()
        mMap.addPolyline(PolylineOptions().apply {
            list.forEach { location ->
                if (abs(mTempLatitude - location.latitude) > 0.00005 || abs(mTempLongitude - location.longitude) > 0.00005) {
                    mTempLatitude = location.latitude
                    mTempLongitude = location.longitude
                    this.add(LatLng(location.latitude, location.longitude))
                }
            }
        })
    }

    fun approximateDistanceOfMeter(fromLocation: TrackLocation, toLocation: TrackLocation): TrackInformation {
        val from = Location("LocationA")
        from.latitude = fromLocation.latitude
        from.longitude = fromLocation.longitude
        val destination = Location("LocationB")
        destination.latitude = toLocation.latitude
        destination.longitude = toLocation.longitude
        return TrackInformation(
            from.distanceTo(
                destination
            )
        )
    }

    fun screenShotMap(context: Context?, sId: String?) {
        mMap.snapshot { bitmap ->
            File(context?.cacheDir, "$sId.png").writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 85)
        }
        Log.i("tagstorge", "${context?.cacheDir?.absolutePath}/$sId.png")
    }

}