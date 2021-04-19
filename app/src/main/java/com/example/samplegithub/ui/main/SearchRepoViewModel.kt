package com.example.samplegithub.ui.main

import androidx.lifecycle.*
import androidx.paging.cachedIn
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

    var searchApiResponseLD =
        Transformations.switchMap(keywordMutableLiveData) { keyword ->
            githubRepository.getSearchResults(keyword).cachedIn(viewModelScope)
        }

    var totalCountLD =
        Transformations.switchMap(keywordMutableLiveData) { keyword ->
            //todo find way to pass meta data in paging library
             getTotalCount(keyword)
        }
    fun getTotalCount(keyword:String, page:Int? = 1, resultsPerPage:Int? = 1): LiveData<Resource<Int>> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(githubRepository.getTotalCount(keyword, page,resultsPerPage ))
        }
    }
}