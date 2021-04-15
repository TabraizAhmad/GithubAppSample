package com.example.samplegithub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.samplegithub.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: MainActivityBinding

    private val destinationsWithOutBack = setOf(R.id.searchRepoFragment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToolBar()
    }

    private fun setToolBar() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destinationsWithOutBack.contains(destination.id)) {
                binding.appToolbar.visibility = View.GONE

            }else{
                binding.appToolbar.visibility = View.VISIBLE
            }
        }
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }
    }
}