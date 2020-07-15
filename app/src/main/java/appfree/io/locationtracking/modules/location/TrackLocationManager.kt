package appfree.io.locationtracking.modules.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

/**
 * Created By Ben on 7/10/20
 */
class TrackLocationManager {

    private var mLocationRequest: LocationRequest? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    var lastLocationListener: ((Location) -> Unit)? = null

    init {
        mLocationRequest = LocationRequest.create()
        /**
         * PRIORITY_HIGH_ACCURACY is more likely to use GPS, and is more likely
         * to use WIFI & Cell tower positioning
         * */
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //interval for active location updates, in milliseconds.
        mLocationRequest?.interval = 1000
    }

    fun registerLocationUpdates(context: Context?) {
        if (context != null) {

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
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            mFusedLocationClient?.requestLocationUpdates(
                mLocationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.lastLocation?.let { location ->
                lastLocationListener?.invoke(location)
            }
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)
        }
    }

    fun removeLocationUpdates() {
        mFusedLocationClient?.removeLocationUpdates(locationCallback)
    }

}