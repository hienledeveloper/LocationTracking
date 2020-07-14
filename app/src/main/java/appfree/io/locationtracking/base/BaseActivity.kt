package appfree.io.locationtracking.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/9/20
 */
abstract class BaseActivity<_ViewDataBinding : ViewDataBinding> : AppCompatActivity() {

    private val permissionViewModel: PermissionViewModel by viewModel()

    abstract fun getLayoutResourceId(): Int

    lateinit var binding: _ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<_ViewDataBinding>(this, getLayoutResourceId())
        binding.lifecycleOwner = this

        onPermissionViewModelObserve()
    }

    private fun onPermissionViewModelObserve() {
        permissionViewModel.notifyPermissionRequest.observe(this, Observer { request ->
            permissionViewModel.getPermissionManager().requestPermission(this, request)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionViewModel.getPermissionManager()
            .onRequestPermissionsResult(requestCode, grantResults) { result ->
                permissionViewModel.notifyPermissionResult.postValue(result)
            }
    }

    protected fun addFragment(containerId: Int, fragment: Fragment, backStack: String?) {
        supportFragmentManager.beginTransaction().add(containerId, fragment)
            .addToBackStack(backStack).commit()
    }

    protected fun replaceFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
    }

}