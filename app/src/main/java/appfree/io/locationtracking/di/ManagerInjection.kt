package appfree.io.locationtracking.di

import appfree.io.locationtracking.modules.permission.PermissionManager
import org.koin.dsl.module

/**
 * Created By Ben on 7/9/20
 */
val managerInjection = module {
    single { PermissionManager() }
}