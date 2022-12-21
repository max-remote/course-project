package com.maks.courseproject.ui.fragments.characters_screens.characters_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentDetailsCharactersBinding
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.episodes_screens.episodes_details.DetailsEpisodesFragment
import com.maks.courseproject.ui.fragments.location_screens.location_details.DetailsLocationFragment
import com.maks.courseproject.utils.showToast
import kotlinx.coroutines.launch

class DetailsCharactersFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private var _binding: FragmentDetailsCharactersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: DetailsCharactersViewModel by viewModels {
        getAppComponent().detailsCharacterViewModelFactory()
    }

    private var episodesAdapter: CharacterEpisodesAdapter = CharacterEpisodesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharacter()
        navigateToBack()
        initData()
        showProgress()
        swipeToRefresh()
        initRecyclerView()
    }

    private fun getCharacter() {
        val id = arguments.let {
            it?.getInt(CHARACTER_ID)
        }
        viewModel.requestCharacter(id ?: throw RuntimeException("id == null"))
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getCharacter()
        }
    }

    private fun showProgress() = with(binding) {
        viewModel.isLoading.observe(viewLifecycleOwner) {
          swipeRefresh.isRefreshing = it
          swipeRefresh.setColorSchemeResources(R.color.progress_color)
        }
    }

    private fun initData() = with(binding) {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    Glide.with(this@DetailsCharactersFragment).load(response.image)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(characterImageView)

                    characterName.text = resources.getString(R.string.character_name, response.name)
                    characterStatus.text =
                        resources.getString(R.string.character_status, response.status)
                    characterSpecies.text =
                        resources.getString(R.string.character_species, response.species)

                    if (response.type.isEmpty()) {
                        characterType.text = resources.getString(R.string.character_type_null)
                    } else {
                        characterType.text =
                            resources.getString(R.string.character_type, response.type)
                    }

                    characterGender.text =
                        resources.getString(R.string.character_gender, response.gender)
                    characterOrigin.text =
                        resources.getString(R.string.character_origin, response.origin.name)

                    characterLocation.text =
                        resources.getString(R.string.character_location, response.location.name)
                }

                characterOrigin.setOnClickListener {
                    onCharacterOriginClicked(response)
                }
                characterLocation.setOnClickListener {
                    onCharacterLocationClicked(response)
                }
                viewModel.requestEpisodes(response.episode)
            }
        }

        viewModel.episodesLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    episodesAdapter.submitList(response)
                }
            }
        }
    }

    private fun onCharacterLocationClicked(response: CharactersResultDTO) {
        val locationCharacterUrl: String = response.location.url
        if (response.location.name == "unknown") {
            showToast("The characters location is ${response.location.name}")
        } else {
            doFragmentNavigation(locationCharacterUrl)
        }
    }

    private fun onCharacterOriginClicked(response: CharactersResultDTO) {
        val originCharacterUrl: String = response.origin.url
        if (response.origin.name == "unknown") {
            showToast("The characters origin is ${response.origin.name}")
        } else {
            doFragmentNavigation(originCharacterUrl)
        }
    }

    private fun doFragmentNavigation(infoUrl: String) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(
                R.id.container, DetailsLocationFragment.newInstance(
                    infoUrl.substringAfterLast('/').toInt()
                )
            ).commit()
    }

    private fun initRecyclerView() {
        binding.episodesRecyclerView.adapter = episodesAdapter
        episodesAdapter.onItemClickListener = { navigateBy ->
            doNavigateToEpisode(navigateBy)
        }
    }

    private fun doNavigateToEpisode(navigateBy: EpisodesResultDTO) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(
                R.id.container, DetailsEpisodesFragment.newInstance(navigateBy.id)
            ).commit()
    }

    private fun navigateToBack() {
        binding.btnBackDescriptionCharacters.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        const val CHARACTER_ID = "CHARACTER_ID"
        fun newInstance(characterItemId: Int) = DetailsCharactersFragment().apply {
            arguments = bundleOf(CHARACTER_ID to characterItemId)
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