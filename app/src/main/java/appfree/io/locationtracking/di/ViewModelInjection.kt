package appfree.io.locationtracking.di

import appfree.io.locationtracking.modules.permission.PermissionViewModel
import appfree.io.locationtracking.ui.fragment.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created By Ben on 7/9/20
 */
val viewModelInjection = module {
    viewModel { MainViewModel() }
    viewModel { PermissionViewModel(get()) }
}