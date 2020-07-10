package appfree.io.locationtracking.ui.fragment.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.databinding.FragmentMainBinding
import appfree.io.locationtracking.modules.location.ForegroundService
import appfree.io.locationtracking.modules.location.TrackLocationManager
import appfree.io.locationtracking.modules.permission.PermissionRequest
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Created By Ben on 7/9/20
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), OnMapReadyCallback {

    private val permissionViewModel: PermissionViewModel by sharedViewModel()

    private lateinit var mMap: GoogleMap
    private val trackLocationManager: TrackLocationManager by inject()
    private var mTempLatitude = 0.0
    private var mTempLongitude = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionViewModel.checkAndRequestPermission(
            activity,
            PermissionRequest.LOCATION_PERMISSION
        )
    }

    override fun onViewModelObserves() {
        super.onViewModelObserves()
        permissionViewModel.notifyPermissionResult.observe(viewLifecycleOwner, Observer { result ->
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
            trackLocationManager.getAllTrackLocations().observe(this, Observer { list ->
                mMap.clear()
                mMap.addPolyline(PolylineOptions().apply {
                    list.forEach {  location ->
                        if (abs(mTempLatitude - location.latitude) > 0.00005 || abs(mTempLongitude - location.longitude) > 0.00005) {
                            mTempLatitude = location.latitude
                            mTempLongitude = location.longitude
                            this.add(LatLng(location.latitude, location.longitude))
                        }
                    }
                    this.jointType(JointType.ROUND)
                })
            })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity?.startForegroundService(
                    Intent(
                        activity,
                        ForegroundService::class.java
                    )
                )
            } else {
                activity?.startService(
                    Intent(
                        activity,
                        ForegroundService::class.java
                    )
                )
            }

        })
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_main

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.isMyLocationEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(10.7847622, 106.7094307)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}