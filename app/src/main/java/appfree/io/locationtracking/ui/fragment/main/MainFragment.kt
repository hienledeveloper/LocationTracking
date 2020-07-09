package appfree.io.locationtracking.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.databinding.FragmentMainBinding
import appfree.io.locationtracking.modules.permission.PermissionRequest
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/9/20
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val permissionViewModel: PermissionViewModel by sharedViewModel()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModelObserves()
        permissionViewModel.checkAndRequestPermission(
            activity,
            PermissionRequest.LOCATION_PERMISSION
        )
    }

    private fun onViewModelObserves() {
        permissionViewModel.notifyPermissionResult.observe(viewLifecycleOwner, Observer { result ->

        })
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_main

}