package com.salman.gitsy.domain.remote.beans


import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var userBeans: List<UserBean>?,
    @SerializedName("total_count")
    var totalCount: Int?
)