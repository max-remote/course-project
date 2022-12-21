package com.maks.courseproject.ui.fragments.episodes_screens.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentEpisodesBinding
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.episodes_screens.episodes_details.DetailsEpisodesFragment
import com.maks.courseproject.ui.fragments.episodes_screens.episodes_filter.EpisodesFilterFragment
import com.maks.courseproject.utils.DEFAULT_STRING_QUERY
import kotlinx.coroutines.launch

class EpisodesFragment : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EpisodesViewModel by viewModels {
        getAppComponent().episodesViewModelFactory()
    }

    private var episodesAdapter: EpisodesAdapter = EpisodesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
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
        onButtonFilterClicked()
    }

    private fun onItemClicked() {
        episodesAdapter.onItemClickListener = { location ->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.container,
                    DetailsEpisodesFragment.newInstance(location.id)
                )
                .commit()
        }
    }

    private fun onButtonSearchClicked() = with(binding) {
        btnSearch.setOnClickListener {
            if (!searchView.isVisible) {
                searchView.visibility = View.VISIBLE
                searchEpisode()
            } else {
                searchView.visibility = View.GONE
                binding.searchView.setQuery(DEFAULT_STRING_QUERY, false)
            }
        }
    }

    private fun searchEpisode() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.episodesRecyclerView.scrollToPosition(0)
                viewModel.requestEpisodes(newText)
                return true
            }
        })
    }

    private fun onButtonFilterClicked() = with(binding) {
        btnFilter.setOnClickListener {
            EpisodesFilterFragment().show(requireActivity().supportFragmentManager, "")
            binding.searchView.setQuery(DEFAULT_STRING_QUERY, false)
        }
    }

    private fun swipeToRefresh() = with(binding) {
        swipeRefresh.setOnRefreshListener {
            viewModel.requestEpisodes(DEFAULT_STRING_QUERY)
            searchView.visibility = View.GONE
            searchView.setQuery(DEFAULT_STRING_QUERY, false)
            episodesRecyclerView.scrollToPosition(0)
        }
    }

    private fun showProgress() = with(binding) {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            swipeRefresh.setColorSchemeResources(R.color.progress_color)
        }
    }

    private fun initData() {
        viewModel.episodesLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    viewModel.listData.observe(viewLifecycleOwner) {
                        episodesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.episodesRecyclerView.adapter = episodesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}