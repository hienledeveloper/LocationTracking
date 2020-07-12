package appfree.io.locationtracking.ui.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseActivity
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
        replaceFragment(binding.fragmentContainer.id, HistoryFragment())
        viewModel.notifyNavigation.observe(this, Observer { event ->
            when (event) {
                DestinationEvent.RECORD -> {
                    addFragment(binding.fragmentContainer.id, RecordFragment(), "backStack")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
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