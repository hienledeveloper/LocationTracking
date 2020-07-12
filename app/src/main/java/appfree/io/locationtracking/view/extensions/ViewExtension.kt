package appfree.io.locationtracking.view.extensions

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created By Ben on 7/11/20
 */

@BindingAdapter("viewVisibility")
fun View.viewVisibility(isVisibility: Boolean) {
    this.visibility = if (isVisibility) View.VISIBLE else View.GONE
}
