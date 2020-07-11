package appfree.io.locationtracking.di

import appfree.io.locationtracking.modules.location.TrackLocationManager
import appfree.io.locationtracking.modules.map.TrackMapManager
import appfree.io.locationtracking.modules.permission.PermissionManager
import appfree.io.locationtracking.modules.room.dao.TrackLocationDao
import appfree.io.locationtracking.modules.sharepreference.SharedPreferencesManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created By Ben on 7/9/20
 */
val managerInjection = module {
    single { PermissionManager() }
    single { TrackLocationManager(get(),get()) }
    factory { TrackMapManager() }
    single { SharedPreferencesManager(androidApplication()) }
}