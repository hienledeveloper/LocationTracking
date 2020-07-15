package appfree.io.locationtracking.ui.activity.main

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseActivity
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.data.local.DestinationEvent
import appfree.io.locationtracking.databinding.ActivityMainBinding
import appfree.io.locationtracking.modules.location.RecordLocationService
import appfree.io.locationtracking.ui.fragment.history.HistoryFragment
import appfree.io.locationtracking.ui.fragment.record.RecordFragment
import appfree.io.locationtracking.utils.ServiceUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init first screen
        replaceFragment(binding.fragmentContainer.id, HistoryFragment())

        // notify when user change screens
        viewModel.notifyNavigation.observe(this, Observer { event ->
            when (event) {
                DestinationEvent.RECORD -> {
                    addFragment(binding.fragmentContainer.id, RecordFragment(), "backStack")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        supportFragmentManager.removeOnBackStackChangedListener(fragmentManagerListener)
    }

    private val fragmentManagerListener = FragmentManager.OnBackStackChangedListener {
        supportFragmentManager.findFragmentById(binding.fragmentContainer.id).let { fragment ->
            if (fragment is BaseFragment<*>) {
                fragment.onFragmentResume()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener(fragmentManagerListener)

        // check state service tracking
        if (ServiceUtil.hasServiceRunning(applicationContext, RecordLocationService::class.java)) {
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)?.let { fragment ->
                if (fragment !is RecordFragment) {
                    addFragment(binding.fragmentContainer.id, RecordFragment(), "backStack")
                }
            }
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

}