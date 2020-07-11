package appfree.io.locationtracking.modules.room

import androidx.room.Database
import androidx.room.RoomDatabase
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.modules.room.dao.TrackLocationDao
import appfree.io.locationtracking.modules.room.dao.TrackSessionDao

/**
 * Created By Ben on 7/10/20
 */
@Database(entities = [TrackLocation::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun trackLocationDao(): TrackLocationDao
    abstract fun trackSessionDao(): TrackSessionDao
}