package com.maks.courseproject.ui.fragments.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maks.courseproject.databinding.FragmentEpisodesBinding
import com.maks.courseproject.getAppComponent

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

        viewModel.episodesLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                episodesAdapter.submitList(response.results)
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