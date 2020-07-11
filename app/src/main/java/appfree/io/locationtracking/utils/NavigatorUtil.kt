package appfree.io.locationtracking.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * Created By Ben on 7/10/20
 */
object NavigatorUtil {

    fun <T> startForegroundService(context: Context?, clazz: Class<T>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(
                Intent(
                    context,
                    clazz
                )
            )
        } else {
            context?.startService(
                Intent(
                    context,
                    clazz
                )
            )
        }
    }

    fun <T> stopService(context: Context?, clazz: Class<T>) {
        context?.stopService(Intent(context, clazz))
    }

}