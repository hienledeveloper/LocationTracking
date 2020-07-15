package appfree.io.locationtracking.modules.location

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import appfree.io.locationtracking.R
import appfree.io.locationtracking.modules.room.dao.TrackLocationDao
import appfree.io.locationtracking.ui.activity.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

/**
 * Created By Ben on 7/10/20
 */
class RecordLocationService : Service() {

    companion object {
        const val RECORD_SESSION_ID = "record_session_id"
        private const val RECORD_NOTIFICATION_CHANNEL_ID = "notification_track_me"
    }

    private val binder: LocationServiceBinder = LocationServiceBinder(this)
    private val locationManager: TrackLocationManager by inject()
    private val trackLocationDao: TrackLocationDao by inject()
    private var hasStopService = false

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.getStringExtra(RECORD_SESSION_ID)?.let { sessionId ->
            tracking(sessionId)
        }
        return START_STICKY
    }

    private fun tracking(sessionId: String) {
        locationManager.registerLocationUpdates(applicationContext)
        locationManager.lastLocationListener = { location ->
            if (hasStopService) {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        trackLocationDao.insert(
                            TrackLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                sessionId = sessionId,
                                updatedAt = System.currentTimeMillis()
                            ))
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(
                132131, getNotification()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(): Notification {
        val channel =
            NotificationChannel(
                RECORD_NOTIFICATION_CHANNEL_ID,
                "Tracking Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
        getSystemService(NotificationManager::class.java).apply {
            this?.createNotificationChannel(channel)
        }
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return Notification.Builder(this, RECORD_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getText(R.string.app_name))
            .setContentText("App is running in background")
            .setSmallIcon(android.R.drawable.ic_menu_directions)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(false)
            .setOngoing(true)
            .setTicker(getText(R.string.ticker_text))
            .build()
    }

    override fun stopService(name: Intent?): Boolean {
        locationManager.removeLocationUpdates()
        hasStopService = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
        stopSelf()
        return super.stopService(name)
    }
}