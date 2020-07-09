package appfree.io.locationtracking.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.databinding.FragmentMainBinding
import appfree.io.locationtracking.modules.permission.PermissionManager
import appfree.io.locationtracking.modules.permission.PermissionRequest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/9/20
 */
class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val permissionManager: PermissionManager by inject()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionManager.requestPermission(activity, PermissionRequest.LOCATION_PERMISSION)
    }

}