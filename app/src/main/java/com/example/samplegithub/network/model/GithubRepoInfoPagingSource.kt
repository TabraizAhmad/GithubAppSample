package com.example.samplegithub.network.model

import androidx.paging.PagingSource
import com.example.samplegithub.network.remote.GithubApiService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GithubRepoInfoPagingSource(

    private val apiService: GithubApiService,
    private val query: String
): PagingSource<Int, GithubRepoItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.searchGithubRepositories(query, position, params.loadSize)
            var repoItems:List<GithubRepoItem> = ArrayList()

            response.body()?.items?.let { list->
                repoItems = list
            }

            LoadResult.Page(
                data = repoItems,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repoItems.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

}