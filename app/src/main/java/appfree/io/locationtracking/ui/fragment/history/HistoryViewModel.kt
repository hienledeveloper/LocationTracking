package appfree.io.locationtracking.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import appfree.io.locationtracking.base.BaseViewModel
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.modules.room.dao.TrackSessionDao
import kotlinx.coroutines.runBlocking

/**
 * Created By Ben on 7/11/20
 */
class HistoryViewModel(private val trackSessionDao: TrackSessionDao): BaseViewModel() {

    val sessionObserves = MutableLiveData<List<TrackSession>>()

    fun getAll() {
        runBlocking {
            trackSessionDao.getAll().let {
                sessionObserves.postValue(it)
            }
        }
    }

}