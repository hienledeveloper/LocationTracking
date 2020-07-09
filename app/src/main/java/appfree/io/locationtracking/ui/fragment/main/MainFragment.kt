package appfree.io.locationtracking.ui.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.databinding.FragmentMainBinding
import appfree.io.locationtracking.modules.permission.PermissionRequest
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/9/20
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), OnMapReadyCallback {

    private val permissionViewModel: PermissionViewModel by sharedViewModel()
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var mMap: GoogleMap

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
        })
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_main

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.isMyLocationEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}