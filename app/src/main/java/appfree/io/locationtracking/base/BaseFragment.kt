package appfree.io.locationtracking.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created By Ben on 7/9/20
 */
abstract class BaseFragment<_ViewDataBinding : ViewDataBinding> : Fragment() {

    lateinit var binding: _ViewDataBinding

    abstract fun getLayoutResourceId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<_ViewDataBinding>(
            inflater,
            getLayoutResourceId(),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        onViewModelObserves()
    }

    open fun onViewModelObserves() {}

    open fun onFragmentResume() {}

}