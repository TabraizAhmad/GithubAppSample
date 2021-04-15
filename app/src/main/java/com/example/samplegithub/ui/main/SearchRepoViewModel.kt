package com.example.samplegithub.ui.main

import androidx.lifecycle.*
import com.example.samplegithub.network.model.GithubRepositoryInfo
import com.example.samplegithub.network.model.Resource
import com.example.samplegithub.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchRepoViewModel @Inject constructor(private val githubRepository: GithubRepository): ViewModel() {

    private val keywordMutableLiveData:MutableLiveData<String> = MutableLiveData()

    fun setKeyword(keyword:String){
        keywordMutableLiveData.value = keyword
    }
    var searchApiResponseLD: LiveData<Resource<GithubRepositoryInfo>> =
        Transformations.switchMap(keywordMutableLiveData) { keyword ->
            getSearchApiResponse(keyword)
        }



    fun getSearchApiResponse(keyword:String, page:Int? = null, resultsPerPage:Int? = null): LiveData<Resource<GithubRepositoryInfo>> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(githubRepository.searchGithubRepositories(keyword, page,resultsPerPage ))
        }
    }

}