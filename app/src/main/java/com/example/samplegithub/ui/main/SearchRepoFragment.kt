package com.example.samplegithub.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.samplegithub.R
import com.example.samplegithub.databinding.SearchRepoFragmentBinding

class SearchRepoFragment : Fragment() {

    // Use the 'by viewModels()' Kotlin property delegate
    // from the activity-ktx artifact

    private val viewModel: GithubRepositoryViewModel by viewModels()

    //binding for searching views
    private lateinit var binding: SearchRepoFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = SearchRepoFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}