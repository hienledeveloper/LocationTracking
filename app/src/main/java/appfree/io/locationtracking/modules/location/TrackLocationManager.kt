package appfree.io.locationtracking.modules.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import appfree.io.locationtracking.modules.room.dao.TrackLocationDao
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created By Ben on 7/10/20
 */
class TrackLocationManager(private val dao: TrackLocationDao) {

    private var mLocationRequest: LocationRequest? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null

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
                Log.i("location", "latitude - ${location.latitude}")
                Log.i("location", "longitude - ${location.longitude}")
                GlobalScope.launch {
                    saveTrackLocation(TrackLocation(latitude = location.latitude, longitude = location.longitude))
                }
            }
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)
        }
    }

    fun removeLocationUpdates() {
        mFusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    suspend fun saveTrackLocation(trackLocation: TrackLocation) {
        withContext(Dispatchers.IO) {
            dao.insert(trackLocation)
        }
    }

    fun getAllTrackLocations(): LiveData<List<TrackLocation>> = dao.getAll()

}