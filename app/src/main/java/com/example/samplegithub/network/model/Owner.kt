package com.example.samplegithub.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val avatar_url: String,
    val id: Int
) : Parcelable