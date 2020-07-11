package appfree.io.locationtracking.utils

import android.app.ActivityManager
import android.content.Context

/**
 * Created By Ben on 7/11/20
 */
object ServiceUtil {

    @SuppressWarnings("deprecation")
    inline fun <reified T> hasServiceRunning(context: Context?, clazz: Class<T>): Boolean {
        if (context==null) return false
        context.getSystemService(Context.ACTIVITY_SERVICE)?.let { manager ->
            if (manager is ActivityManager) {
                manager.getRunningServices(Integer.MAX_VALUE).forEach { runServiceInfo ->
                    if (runServiceInfo.service.className.contentEquals(clazz.name)) {
                        return true
                    }
                }
            }
        }
        return false
    }

}