package appfree.io.locationtracking.ui.activity.main

import androidx.lifecycle.MutableLiveData
import appfree.io.locationtracking.base.BaseViewModel
import appfree.io.locationtracking.data.local.DestinationEvent

/**
 * Created By Ben on 7/11/20
 */
class MainViewModel: BaseViewModel() {

    val notifyNavigation = MutableLiveData<DestinationEvent>()
    val notifyBackPressed = MutableLiveData<String>()

}