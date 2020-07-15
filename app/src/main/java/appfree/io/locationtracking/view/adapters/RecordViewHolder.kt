package appfree.io.locationtracking.view.adapters

import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import appfree.io.locationtracking.data.local.TrackInformation
import appfree.io.locationtracking.data.local.TrackSession
import appfree.io.locationtracking.databinding.ViewHolerRecordBinding
import java.io.File

/**
 * Created By Ben on 7/11/20
 */
class RecordViewHolder(val binding: ViewHolerRecordBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(trackSession: TrackSession) {
        binding.data = TrackInformation(trackSession.distance, trackSession.duration)
        File(itemView.context?.filesDir, "${trackSession.uid}.png").let { file ->
            if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath).let { bitmap ->
                    binding.image.setImageBitmap(bitmap)
                }
            }
        }
    }

}