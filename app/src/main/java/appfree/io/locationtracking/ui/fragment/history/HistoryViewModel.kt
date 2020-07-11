package appfree.io.locationtracking.ui.fragment.history

import appfree.io.locationtracking.base.BaseViewModel
import appfree.io.locationtracking.modules.room.dao.TrackSessionDao

/**
 * Created By Ben on 7/11/20
 */
class HistoryViewModel(private val trackSessionDao: TrackSessionDao): BaseViewModel() {

    fun getAll() = trackSessionDao.getAll()

}