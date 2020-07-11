package appfree.io.locationtracking.di

import androidx.room.Room
import appfree.io.locationtracking.modules.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created By Ben on 7/10/20
 */
val roomInjection = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "db_track_location")
            .fallbackToDestructiveMigration().build()
    }
    factory { get<AppDatabase>().trackLocationDao() }
    factory { get<AppDatabase>().trackSessionDao() }
}