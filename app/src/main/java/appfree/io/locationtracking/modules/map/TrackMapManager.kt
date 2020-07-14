package appfree.io.locationtracking.modules.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.view.extensions.writeBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import java.io.File

/**
 * Created By Ben on 7/10/20
 */
class TrackMapManager {

    private lateinit var mMap: GoogleMap
    private var currentLocation: Location? = null

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

    fun moveCameraTo(location: Location) {
        if (currentLocation == null) {
            val myLocation = LatLng(location.latitude, location.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
        }
        currentLocation = location
    }

    fun drawPolyline(list: List<TrackLocation>) {
        mMap.clear()
        mMap.addPolyline(PolylineOptions().apply {
            list.forEach { location ->
                this.add(LatLng(location.latitude, location.longitude))
            }
        })
    }

    fun approximateDistanceOfMeter(fromLocation: TrackLocation, toLocation: TrackLocation): Float {
        val from = Location("LocationA")
        from.latitude = fromLocation.latitude
        from.longitude = fromLocation.longitude
        val destination = Location("LocationB")
        destination.latitude = toLocation.latitude
        destination.longitude = toLocation.longitude
        return from.distanceTo(destination)
    }

    fun screenShotMap(context: Context?, sId: String?) {
        mMap.snapshot { bitmap ->
            File(context?.externalCacheDir, "$sId.png").writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 85)
        }
    }

}