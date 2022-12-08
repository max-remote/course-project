package com.maks.courseproject.ui.fragments.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.databinding.FragmentCharactersBinding
import com.maks.courseproject.getAppComponent
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels {
        getAppComponent().charactersViewModelsFactory()
    }
    private var charactersAdapter: CharactersAdapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    viewModel.listData.collect() {
                        charactersAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.charactersRecyclerView.adapter = charactersAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}