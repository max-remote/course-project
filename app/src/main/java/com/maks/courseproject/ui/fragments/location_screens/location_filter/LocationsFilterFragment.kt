package com.maks.courseproject.ui.fragments.location_screens.location_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maks.courseproject.databinding.FragmentFilterLocationsBinding
import com.maks.courseproject.getAppComponent
import com.maks.courseproject.ui.fragments.location_screens.location.LocationViewModel

class LocationsFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFilterLocationsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LocationViewModel by viewModels {
        getAppComponent().locationViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doWrapContentPeekHeight(view)

        onButtonBackPressed()
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