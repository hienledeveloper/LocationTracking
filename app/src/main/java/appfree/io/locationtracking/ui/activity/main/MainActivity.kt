package appfree.io.locationtracking.ui.activity.main

import android.os.Bundle
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseActivity
import appfree.io.locationtracking.databinding.ActivityMainBinding
import appfree.io.locationtracking.ui.fragment.record.RecordFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(binding.fragmentContainer.id, RecordFragment())
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

}