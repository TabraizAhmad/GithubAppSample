package com.example.samplegithub.ui.main

import androidx.lifecycle.*
import com.example.samplegithub.network.model.GithubRepositoryInfo
import com.example.samplegithub.network.model.RepoReleaseInfo
import com.example.samplegithub.network.model.Resource
import com.example.samplegithub.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(private val githubRepository: GithubRepository): ViewModel() {

    fun getLatestRelease(releaseURL:String)
            : LiveData<Resource<RepoReleaseInfo>>{

        return liveData(viewModelScope.coroutineContext +Dispatchers.IO) {
            emit( githubRepository.getLatestRelease(releaseURL) )
        }
    }
}