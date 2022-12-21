package com.maks.courseproject.ui.fragments.characters_screens.characters_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maks.courseproject.R
import com.maks.courseproject.databinding.FragmentFilterCharactersBinding
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.characters_screens.characters.CharactersViewModel

class CharacterFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFilterCharactersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels {
        getAppComponent().charactersViewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doWrapContentPeekHeight(view)
        onButtonBackPressed()
        addHintTypeSearch()
        onApplyButtonClick()
    }

    private fun onApplyButtonClick() = with(binding){
        btnApply.setOnClickListener {
            dismiss()
        }
    }

    private fun doWrapContentPeekHeight(view: View) {
        view.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val behavior = BottomSheetBehavior.from(view.parent as View)
                behavior.peekHeight = view.height
                view.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun addHintTypeSearch() {
        val hints = resources.getStringArray(R.array.characters_hints)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            hints
        )
        binding.inputEditTextCharacterType.setAdapter(adapter)
    }

    private fun onButtonBackPressed() = with(binding) {
        btnHide.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}