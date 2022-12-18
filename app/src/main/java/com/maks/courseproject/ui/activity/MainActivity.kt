package com.maks.courseproject.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.maks.courseproject.R
import com.maks.courseproject.databinding.ActivityMainBinding
import com.maks.courseproject.ui.fragments.characters.CharactersFragment
import com.maks.courseproject.ui.fragments.episodes.EpisodesFragment
import com.maks.courseproject.ui.fragments.location.LocationFragment
import com.maks.courseproject.data.network.utils.ConnectionLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.isLoading.value } }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharactersFragment())
                .commit()
        }
        setItemMenuSelect()
        observeNetwork()
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this) { isNetworkAvailable ->
            when (isNetworkAvailable) {
                true -> binding.networkMessageFrame.visibility = View.GONE
                else -> binding.networkMessageFrame.visibility = View.VISIBLE
            }
        }
    }

    private fun setItemMenuSelect() {
        binding.bottomAppBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_view_characters -> {
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    doBottomFragmentNavigate(CharactersFragment())
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
            .addToBackStack("")
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val currentFragment =
            this@MainActivity.supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is CharactersFragment) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Leaving app")
                .setMessage("The Pickle Rick said that you want to leaving app. Are you sure?")
                .setIcon(R.drawable.ic_characters_menu)
                .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                    super.onBackPressed()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, whichButton -> }
                .show()
        } else {
            super.onBackPressed()
        }
    }
}