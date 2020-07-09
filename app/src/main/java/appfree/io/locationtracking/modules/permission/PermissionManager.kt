package appfree.io.locationtracking.modules.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

/**
 * Created By Ben on 7/9/20
 */
class PermissionManager {

    fun checkPermissionHasGranted(context: Context, request: PermissionRequest): Boolean {
        var isPermissionGranted = false
        PermissionEvent(request).listPermissions.forEach { permissionString ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permissionString
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                isPermissionGranted = true
            }
        }
        return isPermissionGranted
    }

    private fun requestPermission(activity: Activity?, request: PermissionRequest) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.requestPermissions(
                PermissionEvent(request).listPermissions,
                request.code
            )
        }
    }

    fun requestPermission(activity: FragmentActivity?, request: PermissionRequest) {
        requestPermission(activity as Activity, request)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray,
        callback: (PermissionResult) -> Unit
    ) {
        callback.invoke(
            PermissionResult(
                when (requestCode) {
                    PermissionRequest.LOCATION_PERMISSION.code -> PermissionRequest.LOCATION_PERMISSION
                    else -> null
                }
                , grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            )
        )
    }

}