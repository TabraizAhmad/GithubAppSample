package com.example.samplegithub.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.samplegithub.network.model.GithubRepoInfoPagingSource
import com.example.samplegithub.network.model.GithubRepositoryInfo
import com.example.samplegithub.network.model.RepoReleaseInfo
import com.example.samplegithub.network.model.Resource
import com.example.samplegithub.network.remote.GithubApiService
import retrofit2.http.Query
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(private val apiService: GithubApiService) {



    suspend fun getTotalCount(keyword: String, page: Int?, resultsPerPage: Int?): Resource<Int>{
        return try {
            val response = apiService.searchGithubRepositories(keyword, page, resultsPerPage)
            if(response.isSuccessful){
                Resource.Success(response.body()?.total_count)
            }else{
                Resource.Error(Exception(response.message()))
            }
        }catch (e:Exception){
            Resource.Error(Exception(e.message))
        }

    }


    suspend fun getLatestRelease(releaseURL: String): Resource<RepoReleaseInfo>{
        return try {
            val response = apiService.getLatestRelease(releaseURL)
            if(response.isSuccessful){
                Resource.Success(response.body())
            }else{
                Resource.Error(Exception(response.message()))
            }
        }catch (e:Exception){
            Resource.Error(Exception(e.message))
        }
    }


    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubRepoInfoPagingSource(apiService, query) }
        ).liveData

}