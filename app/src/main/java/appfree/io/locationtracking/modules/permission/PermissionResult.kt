package appfree.io.locationtracking.modules.permission

/**
 * Created By Ben on 7/9/20
 */
data class PermissionResult (
    val permissionRequest: PermissionRequest?,
    val isGranted: Boolean
)