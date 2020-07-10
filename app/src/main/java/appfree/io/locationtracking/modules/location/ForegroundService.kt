package appfree.io.locationtracking.modules.location

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import appfree.io.locationtracking.ui.activity.main.MainActivity
import org.koin.android.ext.android.inject

/**
 * Created By Ben on 7/10/20
 */
class ForegroundService : Service() {

    companion object {
        private const val ACTION_START_FOREGROUND_SERVICE = ""
        private const val ACTION_STOP_FOREGROUND_SERVICE = ""
    }

    private val binder: LocationServiceBinder = LocationServiceBinder(this)
    private val locationManager: TrackLocationManager by inject()

    override fun onBind(intent: Intent?): IBinder? = binder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        locationManager.registerLocationUpdates(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(
                132131, getNotification()
            )
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.action.let { action ->
            when (action) {
                ACTION_START_FOREGROUND_SERVICE -> {}
                ACTION_STOP_FOREGROUND_SERVICE -> {}
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeLocationUpdates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(): Notification {
        val channel =
            NotificationChannel(
                "tracking_me",
                "Tracking Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        getSystemService(NotificationManager::class.java).apply {
            this?.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(applicationContext, "tracking_me").apply {
            this.setAutoCancel(true)
            this.setContentText("Tracking your location")
            this.setFullScreenIntent(pendingIntent, true)
            this.setContentIntent(pendingIntent)
        }
        return builder.build()
    }
}