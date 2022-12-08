package com.maks.courseproject.ui.fragments.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.databinding.FragmentEpisodesBinding
import com.maks.courseproject.getAppComponent
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
        initViewModel()
        showProgress()
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.requestEpisodes()
        }
    }

    private fun showProgress() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }
    }


    private fun initViewModel() {
        viewModel.episodesLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    viewModel.listData.collect() {
                        episodesAdapter.submitData(it)
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