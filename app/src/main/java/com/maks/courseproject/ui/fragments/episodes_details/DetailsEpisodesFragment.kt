package com.maks.courseproject.ui.fragments.episodes_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maks.courseproject.databinding.FragmentDetailsEpisodesBinding
import com.maks.courseproject.ui.fragments.characters.CharactersViewModel

class DetailsEpisodesFragment : Fragment() {

        private var _binding: FragmentDetailsEpisodesBinding? = null
        private val binding
            get() = _binding!!

        private val viewModel: CharactersViewModel by activityViewModels()

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
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }