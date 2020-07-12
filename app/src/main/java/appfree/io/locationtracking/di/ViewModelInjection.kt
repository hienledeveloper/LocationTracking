package appfree.io.locationtracking.di

import appfree.io.locationtracking.modules.permission.PermissionViewModel
import appfree.io.locationtracking.ui.activity.main.MainViewModel
import appfree.io.locationtracking.ui.fragment.history.HistoryViewModel
import appfree.io.locationtracking.ui.fragment.record.RecordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created By Ben on 7/9/20
 */
val viewModelInjection = module {
    viewModel { MainViewModel() }
    viewModel { RecordViewModel(get()) }
    viewModel { PermissionViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}