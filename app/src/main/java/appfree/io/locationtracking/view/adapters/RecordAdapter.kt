package appfree.io.locationtracking.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import appfree.io.locationtracking.R
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.databinding.ViewHolerRecordBinding

/**
 * Created By Ben on 7/11/20
 */
class RecordAdapter: RecyclerView.Adapter<RecordViewHolder>() {

    val listSessions = mutableListOf<TrackSession>()

    override fun getItemViewType(position: Int): Int = R.layout.view_holer_record

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding: ViewHolerRecordBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        return RecordViewHolder(binding)
    }

    override fun getItemCount(): Int = listSessions.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(listSessions[position])
    }
}