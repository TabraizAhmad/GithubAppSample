package com.example.samplegithub.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.samplegithub.R

class SearchRepoFragment : Fragment() {

    companion object {
        fun newInstance() = SearchRepoFragment()
    }

    private lateinit var viewModel: GithubRepositoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.search_repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GithubRepositoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}