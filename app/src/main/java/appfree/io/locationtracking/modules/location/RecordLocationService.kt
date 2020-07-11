package appfree.io.locationtracking.modules.location

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import appfree.io.locationtracking.R
import appfree.io.locationtracking.ui.activity.main.MainActivity
import org.koin.android.ext.android.inject

/**
 * Created By Ben on 7/10/20
 */
class RecordLocationService : Service() {

    companion object {
        private const val RECORD_NOTIFICATION_CHANNEL_ID = "notification_track_me"
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
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(): Notification {
        val channel =
            NotificationChannel(
                RECORD_NOTIFICATION_CHANNEL_ID,
                "Tracking Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        getSystemService(NotificationManager::class.java).apply {
            this?.createNotificationChannel(channel)
        }
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return Notification.Builder(this, RECORD_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.ticker_text))
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeLocationUpdates()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager::class.java).apply {
                this?.deleteNotificationChannel(RECORD_NOTIFICATION_CHANNEL_ID)
            }
        }
    }

    override fun stopService(name: Intent?): Boolean {
        locationManager.removeLocationUpdates()
        return super.stopService(name)
    }
}