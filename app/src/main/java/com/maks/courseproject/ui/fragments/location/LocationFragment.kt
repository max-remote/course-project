package com.maks.courseproject.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maks.courseproject.databinding.FragmentLocationBinding
import com.maks.courseproject.getAppComponent

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

        viewModel.locationsLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                locationAdapter.submitList(response.results)
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