package appfree.io.locationtracking.modules.location

import android.os.Binder

/**
 * Created By Ben on 7/10/20
 */
class LocationServiceBinder(private val recordLocationService: RecordLocationService): Binder() {

    fun getService(): RecordLocationService {
        return recordLocationService
    }

}