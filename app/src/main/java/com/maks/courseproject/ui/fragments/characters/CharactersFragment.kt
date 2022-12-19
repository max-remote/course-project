package com.maks.courseproject.ui.fragments.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentCharactersBinding
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.characters_details.DetailsCharactersFragment
import com.maks.courseproject.utils.DEFAULT_QUERY
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
        initData()
        showProgress()
        swipeToRefresh()
        onButtonSearchClicked()
        onItemClicked()
        searchCharacter()
    }

    private fun searchCharacter() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.requestCharacter(newText)
                binding.charactersRecyclerView.scrollToPosition(0)
                return true
            }
        })
    }

    private fun onItemClicked() {
        charactersAdapter.onItemClickListener = { character ->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.container,
                    DetailsCharactersFragment.newInstance(character.id)
                )
                .commit()
        }
    }

    private fun onButtonSearchClicked() = with(binding) {
        btnSearch.setOnClickListener {
            if (!searchView.isVisible) {
                searchView.visibility = View.VISIBLE
            } else {
                searchView.visibility = View.GONE
            }
        }
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.requestCharacter(DEFAULT_QUERY)
            binding.searchView.visibility = View.GONE
        }
    }

    private fun initData() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch {
                    viewModel.listData.observe(viewLifecycleOwner) {
                        charactersAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.charactersRecyclerView.adapter = charactersAdapter
    }

    private fun showProgress() = with(binding) {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            swipeRefresh.setColorSchemeResources(R.color.progress_color)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}