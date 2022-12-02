package com.maks.courseproject.ui.episodes.episodes_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentEpisodesBinding
import com.maks.courseproject.ui.characters.characters_fragment.CharactersViewModel
import com.maks.courseproject.ui.episodes.details_episodes_fragment.DetailsEpisodesFragment

class EpisodesFragment : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CharactersViewModel by activityViewModels()

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
        navigateToEpisodesDetails()
    }

    private fun navigateToEpisodesDetails() {
        binding.btnTestNavToEpisodeDescription.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsEpisodesFragment())
                .addToBackStack("")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}