package com.maks.courseproject.ui.fragments.episodes_screens.episodes_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentDetailsEpisodesBinding
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.characters_screens.characters_details.DetailsCharactersFragment
import kotlinx.coroutines.launch

class DetailsEpisodesFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private var _binding: FragmentDetailsEpisodesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: DetailsEpisodesViewModel by viewModels {
        getAppComponent().detailsEpisodeViewModelFactory()
    }

    private var charactersAdapter: DetailEpisodeCharactersAdapter = DetailEpisodeCharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        getEpisodes()
        initData()
        showProgress()
        swipeToRefresh()
        initRecyclerView()
    }

    private fun getEpisodes() {
        val id = arguments.let {
            it?.getInt(EPISODE_ID)
        }
        viewModel.requestEpisode(id ?: throw RuntimeException("id == null"))
    }

    private fun initData() = with(binding) {
        viewModel.episodesLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    episodeName.text = resources.getString(R.string.episode_name, response.name)
                    episodeNumber.text = resources.getString(R.string.episode_number, response.episode)
                    episodeAir.text = resources.getString(R.string.episode_air, response.air_date)
                }
            }
            viewModel.requestResidents(response.characters)
        }
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    charactersAdapter.submitList(response)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.charactersRecyclerView.adapter = charactersAdapter
        charactersAdapter.onItemClickListener = { navigateBy ->
            doNavigateToCharacters(navigateBy)
        }
    }

    private fun doNavigateToCharacters(navigateBy: CharactersResultDTO) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(
                R.id.container, DetailsCharactersFragment.newInstance(navigateBy.id)
            ).commit()
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getEpisodes()
        }
    }

    private fun showProgress() = with(binding) {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            swipeRefresh.setColorSchemeResources(R.color.progress_color)
        }
    }

    private fun navigateBack() {
        binding.btnBackDescriptionHeader.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        const val EPISODE_ID = "EPISODE_ID"
        fun newInstance(episodeItemId: Int) =
            DetailsEpisodesFragment().apply {
                arguments = bundleOf(EPISODE_ID to episodeItemId)
            }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.swipeRefresh.isEnabled = verticalOffset == 0
    }

    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        binding.appBar.removeOnOffsetChangedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}