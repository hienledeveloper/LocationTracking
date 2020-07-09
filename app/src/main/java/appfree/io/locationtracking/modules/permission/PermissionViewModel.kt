package appfree.io.locationtracking.modules.permission

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import appfree.io.locationtracking.base.BaseViewModel

/**
 * Created By Ben on 7/9/20
 */
class PermissionViewModel(private val manager: PermissionManager): BaseViewModel() {

    val notifyPermissionRequest = MutableLiveData<PermissionRequest>()

    val notifyPermissionResult = MutableLiveData<PermissionResult>()

    fun getPermissionManager() = manager

    fun checkAndRequestPermission(activity: FragmentActivity?, request: PermissionRequest) {
        manager.checkPermissionHasGranted(activity, request).let { hasGranted ->
            if (hasGranted)
                notifyPermissionResult.postValue(PermissionResult(request, hasGranted))
            else {
                notifyPermissionRequest.postValue(PermissionRequest.LOCATION_PERMISSION)
            }
        }
    }



}