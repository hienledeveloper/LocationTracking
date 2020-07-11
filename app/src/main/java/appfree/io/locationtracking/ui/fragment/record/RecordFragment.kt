package appfree.io.locationtracking.ui.fragment.record

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.data.local.RecordState
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.databinding.FragmentMainBinding
import appfree.io.locationtracking.modules.location.RecordLocationService
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.modules.location.TrackLocationManager
import appfree.io.locationtracking.modules.map.TrackMapManager
import appfree.io.locationtracking.modules.permission.PermissionRequest
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import appfree.io.locationtracking.modules.sharepreference.SharedPreferencesManager
import appfree.io.locationtracking.utils.NavigatorUtil
import appfree.io.locationtracking.utils.ServiceUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created By Ben on 7/9/20
 */
class RecordFragment : BaseFragment<FragmentMainBinding>(), OnMapReadyCallback {

    private val permissionViewModel: PermissionViewModel by sharedViewModel()
    private val trackLocationManager: TrackLocationManager by inject()
    private val trackMapManager: TrackMapManager by inject()
    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    private val recordViewModel: RecordViewModel by viewModel()

    private var mPreviousLocation: TrackLocation? = null
    private var fTotalDistance: Float = 0f

    override fun getLayoutResourceId(): Int = R.layout.fragment_main

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
            if (result.isGranted) {
                permissionReady()
            }
        })
    }

    private fun permissionReady() {
        setUpView()
        loadGoogleMap()
    }

    private fun setUpView() {
        binding.viewModel = recordViewModel
        if (hasRecording()) {

            // state recording
            binding.state = RecordState.RECORD_START
        } else {

            // new & prepare session
            sharedPreferencesManager.currentSessionId = UUID.randomUUID().toString()
            binding.state = RecordState.RECORD_STOP
        }

        viewModelObserve()
    }

    private fun loadGoogleMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        trackMapManager.setUp(context, googleMap)
        // Add a marker in Sydney and move the camera
        trackLocationManager.lastLocationListener = { trackLocation ->
            trackMapManager.moveCameraTo(trackLocation)
        }
        trackLocationManager.getAllTrackLocationsByCurrentSession().observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                fTotalDistance = trackMapManager.approximateDistanceOfMeter(list.first(), list.last()).distance
                trackMapManager.drawPolyline(list)
                list.last().let { currentLocation ->
                    mPreviousLocation?.let { fromLocation ->
                        trackMapManager.approximateDistanceOfMeter(fromLocation, list.last())
                            .let { trackInformation ->
                                binding.data = trackInformation
                            }
                    }
                    mPreviousLocation = currentLocation
                }
            }
        })
    }

    private fun viewModelObserve() {
        recordViewModel.notifyActionClick.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (recordViewModel.notifyActionClick.get()) {
                    RecordState.RECORD_START -> {
                        delayChangeStateAction(RecordState.RECORD_START)
                        startRecord()
                    }
                    RecordState.RECORD_PAUSE -> {
                        delayChangeStateAction(RecordState.RECORD_PAUSE)
                        pauseRecord()
                    }
                    RecordState.RECORD_RESTART -> {
                        delayChangeStateAction(RecordState.RECORD_START)
                        startRecord()
                    }
                    RecordState.RECORD_STOP -> {
                        delayChangeStateAction(RecordState.RECORD_STOP)
                        stopRecord()
                    }
                }

            }

        })
    }

    private fun delayChangeStateAction(state: RecordState) {
        Handler().postDelayed({
            binding.state = state
        }, 500)
    }

    private fun hasRecording() = ServiceUtil.hasServiceRunning(context, RecordLocationService::class.java)

    private fun startRecord() {
        NavigatorUtil.startForegroundService(context, RecordLocationService::class.java)
    }

    private fun pauseRecord() {
        NavigatorUtil.stopService(context, RecordLocationService::class.java)
    }

    private fun stopRecord() {
        trackMapManager.screenShotMap(context, sharedPreferencesManager.currentSessionId)
        NavigatorUtil.stopService(context, RecordLocationService::class.java)
        recordViewModel.saveSession(TrackSession(sharedPreferencesManager.currentSessionId, fTotalDistance))
    }


}