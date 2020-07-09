package appfree.io.locationtracking.modules.permission

import android.Manifest

/**
 * Created By Ben on 7/9/20
 */
class PermissionEvent(private val permissionRequest: PermissionRequest) {
    val listPermissions: Array<String>
    get() = when (permissionRequest.code) {
        PermissionRequest.LOCATION_PERMISSION.code -> arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        else -> arrayOf()
    }
}

enum class PermissionRequest(val code: Int) {
    LOCATION_PERMISSION(100)
}