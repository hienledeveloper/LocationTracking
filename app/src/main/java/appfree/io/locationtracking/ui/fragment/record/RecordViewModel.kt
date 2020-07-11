package appfree.io.locationtracking.ui.fragment.record

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import appfree.io.locationtracking.data.local.RecordState
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.modules.room.dao.TrackSessionDao

/**
 * Created By Ben on 7/9/20
 */
class RecordViewModel(private val dao: TrackSessionDao): ViewModel() {

    val notifyActionClick = ObservableField<RecordState>()

    fun onClickRecordStateChanged(state: RecordState) {
        notifyActionClick.set(state)
    }

    fun saveSession(trackSession: TrackSession) {
        dao.insert(trackSession)
    }

}