package com.maks.courseproject.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.databinding.FragmentLocationBinding
import com.maks.courseproject.getAppComponent
import kotlinx.coroutines.launch

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LocationViewModel by viewModels {
        getAppComponent().locationViewModelFactory()
    }

    private var locationAdapter: LocationsAdapter = LocationsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.locationsLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    viewModel.listData.collect() {
                        locationAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.locationRecyclerView.adapter = locationAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}