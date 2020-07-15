package appfree.io.locationtracking.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle

/**
 * Created By Ben on 7/10/20
 */
object NavigatorUtil {

    fun <T> startForegroundService(context: Context?, clazz: Class<T>, bundle: Bundle? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(
                Intent(
                    context,
                    clazz
                ).apply {
                    if (bundle!=null)
                        this.putExtras(bundle)
                }
            )
        } else {
            context?.startService(
                Intent(
                    context,
                    clazz
                ).apply {
                    if (bundle!=null)
                        this.putExtras(bundle)
                }
            )
        }
    }

    fun <T> stopService(context: Context?, clazz: Class<T>) {
        context?.stopService(Intent(context, clazz))
    }

}