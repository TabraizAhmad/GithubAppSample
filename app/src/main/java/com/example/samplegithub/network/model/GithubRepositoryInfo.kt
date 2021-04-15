package com.example.samplegithub.network.model

data class GithubRepositoryInfo(
    val incomplete_results: Boolean,
    val items: List<GithubRepoItem>,
    val total_count: Int
)