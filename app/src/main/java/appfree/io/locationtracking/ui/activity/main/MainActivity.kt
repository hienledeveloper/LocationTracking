package appfree.io.locationtracking.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseActivity
import appfree.io.locationtracking.databinding.ActivityMainBinding
import appfree.io.locationtracking.modules.permission.PermissionViewModel
import appfree.io.locationtracking.ui.fragment.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(binding.fragmentContainer.id, MainFragment())
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

}