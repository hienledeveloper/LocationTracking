package appfree.io.locationtracking.ui.fragment.history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/11/20
 */
class HistoryFragment: BaseFragment<ViewDataBinding>() {

    private val historyViewModel: HistoryViewModel by viewModel()

    override fun getLayoutResourceId(): Int = R.layout.fragment_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel.getAll().observe(viewLifecycleOwner, Observer { list ->
            Log.i("historyList", list.toString())
        })
    }
}