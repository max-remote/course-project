package com.maks.courseproject.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.maks.courseproject.R
import com.maks.courseproject.databinding.ActivityMainBinding
import com.maks.courseproject.ui.fragments.characters.CharactersFragment
import com.maks.courseproject.ui.fragments.episodes.EpisodesFragment
import com.maks.courseproject.ui.fragments.location.LocationFragment

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
        binding.bottomAppBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_view_characters -> {
                    doBottomFragmentNavigate(CharactersFragment())
                    supportFragmentManager.popBackStack(null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
                R.id.action_view_location -> {
                    doBottomFragmentNavigate(LocationFragment())
                }
                R.id.action_view_episodes -> {
                    doBottomFragmentNavigate(EpisodesFragment())
                }
            }
            true
        }
    }

    private fun doBottomFragmentNavigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                androidx.appcompat.R.anim.abc_slide_out_bottom)
            .addToBackStack("")
            .replace(R.id.container, fragment)
            .commit()
    }
}