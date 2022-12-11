package com.maks.courseproject.ui.fragments.location_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentDetailsLocationBinding
import com.maks.courseproject.getAppComponent
import kotlinx.coroutines.launch

class DetailsLocationFragment : Fragment() {

    private var _binding: FragmentDetailsLocationBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: DetailsLocationViewModel by viewModels {
        getAppComponent().detailsLocationViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        getLocation()
        initData()
        showProgress()
        swipeToRefresh()
    }

    private fun getLocation() {
        val id = arguments.let {
            it?.getInt(LOCATION_ID)
        }
        viewModel.requestLocation(id ?: throw RuntimeException("id == null"))
    }

    private fun initData() = with(binding) {
        viewModel.locationLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {

                    locationName.text = resources.getString(R.string.location_name, response.name)

                    locationType.text = resources.getString(R.string.location_type, response.type)

                    if (response.dimension == "unknown") {
                        locationDimension.text =
                            resources.getString(R.string.location_dimension_unknown)
                    } else {
                        locationDimension.text = response.dimension
                    }
                }
            }
        }
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getLocation()
        }
    }

    private fun showProgress() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }
    }

    private fun navigateBack() {
        binding.btnBackDescriptionHeader.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        const val LOCATION_ID = "LOCATION_ID"
        fun newInstance(locationItemId: Int) =
            DetailsLocationFragment().apply {
                arguments = bundleOf(LOCATION_ID to locationItemId)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}