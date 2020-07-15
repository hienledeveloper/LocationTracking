package appfree.io.locationtracking.ui.fragment.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import appfree.io.locationtracking.R
import appfree.io.locationtracking.base.BaseFragment
import appfree.io.locationtracking.data.local.DestinationEvent
import appfree.io.locationtracking.databinding.FragmentHistoryBinding
import appfree.io.locationtracking.ui.activity.main.MainViewModel
import appfree.io.locationtracking.view.adapters.RecordAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created By Ben on 7/11/20
 */
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val historyViewModel: HistoryViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var recordAdapter: RecordAdapter

    override fun getLayoutResourceId(): Int = R.layout.fragment_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordAdapter = RecordAdapter()
        binding.rclSession.apply {
            setHasFixedSize(true)
            adapter = recordAdapter
        }

        // observe to fetch sessions
        historyViewModel.sessionObserves.observe(viewLifecycleOwner, Observer { list ->
            recordAdapter.listSessions.clear()
            recordAdapter.listSessions.addAll(list)
            recordAdapter.notifyDataSetChanged()
            binding.rclSession.scrollTo(0,0)
        })
        historyViewModel.getAll()


        binding.btnRecord.setOnClickListener {
            mainViewModel.notifyNavigation.postValue(DestinationEvent.RECORD)
        }

    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        historyViewModel.getAll()
    }
}