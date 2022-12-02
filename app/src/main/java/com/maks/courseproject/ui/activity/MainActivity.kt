package com.maks.courseproject.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import com.maks.courseproject.R
import com.maks.courseproject.databinding.ActivityMainBinding
import com.maks.courseproject.ui.characters.characters_fragment.CharactersFragment
import com.maks.courseproject.ui.episodes.episodes_fragment.EpisodesFragment
import com.maks.courseproject.ui.location.location_fragment.LocationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharactersFragment())
                .commit()
        }
        itemMenuSelect()
    }

    private fun itemMenuSelect() {
        binding.bottomAppBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_view_characters -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                            androidx.appcompat.R.anim.abc_slide_out_bottom
                        )
                        .addToBackStack("")
                        .replace(R.id.container, CharactersFragment())
                        .commit()
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                R.id.action_view_location -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                            androidx.appcompat.R.anim.abc_slide_out_bottom
                        )
                        .addToBackStack("")
                        .replace(R.id.container, LocationFragment())
                        .commit()
                }
                R.id.action_view_episodes -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                            androidx.appcompat.R.anim.abc_slide_out_bottom
                        )
                        .addToBackStack("")
                        .replace(R.id.container, EpisodesFragment())
                        .commit()
                }
            }
            true
        })
    }
}