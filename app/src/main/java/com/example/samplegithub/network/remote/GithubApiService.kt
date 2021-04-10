package com.example.samplegithub.network.remote

import com.example.samplegithub.network.model.GithubRepositoryInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun searchGithubRepositories(
        @Query("q") keyword: String,
        @Query("page") page: Int?,
        @Query("per_page") resultsPerPage: Int?
    ):  Response<GithubRepositoryInfo>
}