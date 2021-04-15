package com.example.samplegithub.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplegithub.databinding.SearchRepoFragmentBinding
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
    private val viewModel: GithubRepositoryViewModel by viewModels()

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
    }

    private fun initializeRV() {

        binding.searchedReopListRV.layoutManager = LinearLayoutManager(
                context, RecyclerView.VERTICAL, false)
        repoRVAdapter = RepoRVAdapter(repoItems,context, this)
        binding.searchedReopListRV.adapter = repoRVAdapter

    }

    private fun initializeWatcher() {
        binding.searchQueryET.addTextChangedListener(
                DebouncingTextChangeListener(viewLifecycleOwner.lifecycle) { queryText ->
                    queryText?.apply {
                        if(length >= 3)
                            callApiForSearch(this)
                    }
                }
        )
    }

    private fun callApiForSearch(text:String) {
        connectivityLiveData.observe(viewLifecycleOwner, { isAvailable ->

            if(isAvailable){
                viewModel.getSearchApiResponse(text).observe(viewLifecycleOwner,{ resource->
                    when (resource) {
                        is Resource.Error -> {

                        }
                        is Resource.Success -> {
                            repoRVAdapter.setRepoList(resource.data?.items)

                        }
                        is Resource.Loading ->{

                        }
                    }
                })
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onItemClicked(view: View, repoItem: GithubRepoItem) {
        //move to detail adapter
    }

}