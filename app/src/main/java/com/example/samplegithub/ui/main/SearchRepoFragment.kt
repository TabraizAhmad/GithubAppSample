package com.example.samplegithub.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplegithub.R
import com.example.samplegithub.databinding.SearchRepoFragmentBinding
import com.example.samplegithub.extension.makeGone
import com.example.samplegithub.extension.makeVisible
import com.example.samplegithub.network.model.GithubRepoItem
import com.example.samplegithub.network.model.Resource
import com.example.samplegithub.ui.`interface`.DebouncingTextChangeListener
import com.example.samplegithub.ui.adapter.RepoRVAdapter
import com.example.samplegithub.utlis.ConnectivityLiveData
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject


@WithFragmentBindings
@AndroidEntryPoint
class SearchRepoFragment : Fragment(), RepoRVAdapter.OnItemClicked {


    private lateinit var repoRVAdapter:RepoRVAdapter

    private var repoItems = ArrayList<GithubRepoItem>()

    // Use the 'by viewModels()' Kotlin property delegate
    // from the activity-ktx artifact
    private val viewModel: SearchRepoViewModel by viewModels()

    //binding for searching views
    private lateinit var binding: SearchRepoFragmentBinding



    @Inject
    lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchRepoFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeWatcher()
        initializeRV()
        observeResponse()
    }

    private fun initializeRV() {

        binding.searchedReopListRV.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        repoRVAdapter = RepoRVAdapter(repoItems, context, this)
        binding.searchedReopListRV.adapter = repoRVAdapter

    }

    private fun initializeWatcher() {
        binding.searchQueryET.addTextChangedListener(
            DebouncingTextChangeListener(viewLifecycleOwner.lifecycle) { queryText ->
                queryText?.apply {
                    if (length >= 3)
                        viewModel.setKeyword(this)
                }
            }
        )
    }

    private fun observeResponse() {
        viewModel.searchApiResponseLD.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Error -> {
                    binding.progressBar.makeGone()
                    binding.searchedReopListRV.makeGone()
                    binding.resultSizeIndTV.makeGone()
                    binding.errorTextView.makeVisible()
                    binding.errorTextView.text = resource.exception.message

                }
                is Resource.Success -> {
                    binding.progressBar.makeGone()
                    binding.errorTextView.makeGone()
                    binding.resultSizeIndTV.makeVisible()
                    binding.searchedReopListRV.makeVisible()
                    repoRVAdapter.setRepoList(resource.data?.items)
                    binding.resultSizeIndTV.text =  getString(R.string.results, resource.data?.total_count)
                }
                is Resource.Loading -> {
                    binding.progressBar.makeVisible()
                    /*binding.errorTextView.makeGone()
                    binding.searchedReopListRV.makeGone()
                    binding.resultSizeIndTV.makeGone()*/


                }
            }
        })
    }

    override fun onItemClicked(view: View, repoItem: GithubRepoItem) {
        //move to detail adapter
        val action = SearchRepoFragmentDirections.actionSearchRepoFragmentToRepoDetailFragment(
            repoItem
        )
        view.findNavController().navigate(action)
    }

}