package com.example.samplegithub.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoItem(
    val id: Int,
    val updated_at: String,
    val full_name: String,
    val description: String,
    val owner: Owner,
    val forks_count: Int,
    val open_issues_count: Int,
    val stargazers_count: Int,
    val releases_url: String
    ) : Parcelable