package com.example.samplegithub.network.model

data class GithubRepositoryInfo(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)