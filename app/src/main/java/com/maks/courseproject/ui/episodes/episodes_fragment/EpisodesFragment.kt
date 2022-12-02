package com.maks.courseproject.ui.episodes.episodes_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maks.courseproject.R

class EpisodesFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodesFragment()
    }

    private lateinit var viewModel: EpisodesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EpisodesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}