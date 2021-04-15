package com.example.samplegithub.ui.main

import androidx.lifecycle.*
import com.example.samplegithub.network.model.GithubRepositoryInfo
import com.example.samplegithub.network.model.Resource
import com.example.samplegithub.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class GithubRepositoryViewModel @Inject constructor(private val githubRepository: GithubRepository): ViewModel() {
    private var searchApiResponseLD: MutableLiveData<Resource<GithubRepositoryInfo>> = MutableLiveData()
    var searchJob: Job? = null

    fun getSearchApiResponse(keyword:String, page:Int? = null, resultsPerPage:Int? = null)
            : MutableLiveData<Resource<GithubRepositoryInfo>>{

        searchJob?.cancel()
        searchJob = viewModelScope.launch(viewModelScope.coroutineContext +Dispatchers.IO) {
            searchApiResponseLD.postValue(
                githubRepository.searchGithubRepositories(keyword, page,resultsPerPage )
            )
        }
        return searchApiResponseLD
    }
}