package com.maks.courseproject.ui.episodes.details_episodes_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.courseproject.R

class DetailsEpisodesFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsEpisodesFragment()
    }

    private lateinit var viewModel: DetailsEpisodesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_episodes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsEpisodesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}