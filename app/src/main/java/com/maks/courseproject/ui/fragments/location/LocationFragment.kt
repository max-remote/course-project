package com.maks.courseproject.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentLocationBinding
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.location_details.DetailsLocationFragment
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
        initData()
        showProgress()
        swipeToRefresh()
        onItemClicked()
        onButtonSearchClicked()
    }

    private fun onItemClicked() {
            locationAdapter.onItemClickListener = { location ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        R.id.container,
                        DetailsLocationFragment.newInstance(location.id)
                    )
                    .commit()
            }
    }

    private fun onButtonSearchClicked() = with(binding) {
        btnSearch.setOnClickListener {
            if (!searchView.isVisible) {
                searchView.visibility = View.VISIBLE
            } else {
                searchView.visibility = View.GONE
            }
        }
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.requestLocation()
        }
    }

    private fun showProgress() = with(binding) {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            swipeRefresh.setColorSchemeResources(R.color.progress_color)
        }
    }

    private fun initData() {
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