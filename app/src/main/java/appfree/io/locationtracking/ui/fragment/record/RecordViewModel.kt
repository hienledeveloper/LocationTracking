package appfree.io.locationtracking.ui.fragment.record

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import appfree.io.locationtracking.data.local.RecordState
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.modules.location.TrackLocation
import appfree.io.locationtracking.modules.room.dao.TrackLocationDao
import appfree.io.locationtracking.modules.room.dao.TrackSessionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created By Ben on 7/9/20
 */
class RecordViewModel(private val dao: TrackSessionDao,
private val trackLocationDao: TrackLocationDao): ViewModel() {

    val notifyActionClick = ObservableField<RecordState>()

    fun onClickRecordStateChanged(state: RecordState) {
        notifyActionClick.set(state)
    }

    fun saveSession(trackSession: TrackSession) {
        runBlocking {
            withContext(Dispatchers.IO) {
                dao.insert(trackSession)
            }
        }
    }

    fun getAllTrackLocationsByCurrentSession(sId: String): LiveData<List<TrackLocation>> =
        trackLocationDao.getAllBySessionId(sId)

    fun saveTrackLocation(trackLocation: TrackLocation) {
        runBlocking {
            withContext(Dispatchers.IO) {
                trackLocationDao.insert(trackLocation)
            }
        }
    }

}