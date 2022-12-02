package com.maks.courseproject.ui.characters.details_characters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maks.courseproject.R

class DetailsCharactersFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsCharactersFragment()
    }

    private lateinit var viewModel: DetailsCharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_characters, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsCharactersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}